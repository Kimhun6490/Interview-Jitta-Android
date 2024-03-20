package com.mamafarm.android.stockdetails.presentation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.mamafarm.android.stockdetails.R
import com.mamafarm.android.stockdetails.databinding.JittaFragmentStockDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JittaStockDetailsFragment : Fragment() {
    private lateinit var binding: JittaFragmentStockDetailsBinding
    private val viewModel: JittaStockDetailsViewModel by viewModels()

    //TEMP
    private var isFollowing = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = JittaFragmentStockDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setupView()
    }

    private fun setupView() {
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.materialToolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.materialToolbar.setNavigationOnClickListener { findNavController().navigateUp(); }
        binding.etSearch.setOnClickListener {}
        binding.btFollow.setOnClickListener { viewModel.followButtonClicked(!isFollowing) }
    }

    private fun loadData() {
        //GET ARGUMENTS
        val id = arguments?.getString("id")
        val stockId = try {
            arguments?.getString("stockId")?.toInt()
        } catch (_: Exception) {
            null
        }
        val rank = arguments?.getString("rank")
        val total = arguments?.getString("total")

        if (id != null && stockId != null && rank != null && total != null) {
            viewModel.getStockDetail(id, stockId)
            observeViewState(rank, total)
        }
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    private fun observeViewState(rank: String, total: String) {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is JittaStockDetailViewState.Content -> {
                    with(binding) {
                        //TEMP
                        isFollowing = viewState.stockDetail.isFollowing
                        if (isFollowing) {
                            btFollow.text = "unfollow"

                            val color = ContextCompat.getColor(
                                requireContext(),
                                com.mamafarm.android.ui.R.color.md_theme_light_onSurface
                            )
                            btFollow.setBackgroundColor(color)
                        } else {
                            btFollow.text = "follow"

                            val color = ContextCompat.getColor(
                                requireContext(),
                                com.mamafarm.android.ui.R.color.md_theme_light_primary
                            )
                            btFollow.setBackgroundColor(color)
                        }

                        tvStockName.text = viewState.stockDetail.stockName
                        tvStockCode.text = viewState.stockDetail.stockCode
                        tvRanking.text = "Jitta Ranking #$rank from $total"
                    }
                }

                JittaStockDetailViewState.Error -> {
                    Log.i("JittaStockDetailViewState", "ERR")
                }

                JittaStockDetailViewState.Loading -> {
                    Log.i("JittaStockDetailViewState", "LOADING")
                }
            }
        }
    }
}