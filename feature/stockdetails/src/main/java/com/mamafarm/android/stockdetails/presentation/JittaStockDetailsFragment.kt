package com.mamafarm.android.stockdetails.presentation

import android.annotation.SuppressLint
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
        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.app_bar_menu, menu)

                val searchItem = menu.findItem(R.id.app_bar_search)
                val searchView = searchItem.actionView as SearchView
                searchView.isIconifiedByDefault = false
                searchView.setOnQueryTextFocusChangeListener { view, isFocus ->
                    if (isFocus) view.clearFocus()
                    //OPEN SEARCH VIEW
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.app_bar_search -> return true
                    R.id.wise -> {
                        Toast.makeText(requireContext(), "Not supported yet.", Toast.LENGTH_LONG)
                            .show()
                        return true
                    }
                }
                return false
            }

        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            menuProvider,
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
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

    @SuppressLint("SetTextI18n")
    private fun observeViewState(rank: String, total: String) {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is JittaStockDetailViewState.Content -> {
                    with(binding) {
                        tvStockName.text = viewState.stockDetail.stockName
                        tvStockCode.text = viewState.stockDetail.stockCode
                        tvRanking.text = "Jitta Ranking #$rank from $total"
                        btFollow.text = if (viewState.stockDetail.isFollowing) "following"
                        else "follow"
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