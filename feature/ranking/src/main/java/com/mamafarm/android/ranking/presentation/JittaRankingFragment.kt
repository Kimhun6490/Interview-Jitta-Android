package com.mamafarm.android.ranking.presentation

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.mamafarm.android.market.databinding.JittaFragmentMarketBinding
import com.mamafarm.android.ranking.model.JittaCountry
import com.mamafarm.android.ranking.model.JittaSectorType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JittaRankingFragment : Fragment(), AppBarLayout.OnOffsetChangedListener,
    AdapterView.OnItemSelectedListener {
    private lateinit var binding: JittaFragmentMarketBinding
    private lateinit var countryAdapter: ArrayAdapter<JittaCountry>
    private lateinit var sectorAdapter: ArrayAdapter<JittaSectorType>
    private lateinit var rankingAdapter: JittaRankingPagingAdapter
    private val viewModel: JittaRankingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = JittaFragmentMarketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        loadData()
    }

    override fun onResume() {
        super.onResume()
        binding.appBarLayout.addOnOffsetChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        binding.appBarLayout.removeOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
        binding.refreshLayout.isEnabled = p1 == 0
    }

    private fun setupView() {
        setupRecycleView()
        setupSpinner()
    }

    private fun setupSpinner() {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )

        //COUNTRY SPINNER
        countryAdapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            emptyArray<JittaCountry>()
        )
        countryAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        with(binding.spCountry) {
            adapter = countryAdapter
            setSelection(0, false)
            onItemSelectedListener = this@JittaRankingFragment
            layoutParams = params
            setPopupBackgroundResource(R.color.background_light)
        }

        //SECTOR SPINNER
        sectorAdapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            emptyArray<JittaSectorType>()
        )
        sectorAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        with(binding.spFilter) {
            adapter = countryAdapter
            setSelection(0, false)
            onItemSelectedListener = this@JittaRankingFragment
            layoutParams = params
            setPopupBackgroundResource(R.color.background_light)
        }
    }

    private fun setupRecycleView() {
        rankingAdapter = JittaRankingPagingAdapter {
            Toast.makeText(requireContext(), "id ${it.id}", Toast.LENGTH_SHORT).show()
        }
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
        binding.rvRanking.adapter = rankingAdapter
        binding.rvRanking.layoutManager = LinearLayoutManager(context)
        lifecycleScope.launch {
            viewModel.flow.collect { pagingData ->
                rankingAdapter.submitData(pagingData)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            rankingAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        binding.refreshLayout.isRefreshing = false
                    }

                    is LoadState.NotLoading -> {
                        Log.i("paging_data", "not loading")
                    }

                    is LoadState.Error -> {
                        Log.i("paging_data", "error")
                    }
                }
                if (loadState.append.endOfPaginationReached) {
                    if (rankingAdapter.itemCount < 1) {
                        Log.i("paging_data", "end of page")
                    }
                }
            }
        }
    }

    private fun loadData() {
        viewModel.getAvailableCountries()
        viewModel.getListSectorType()
        viewModel.countriesResult.observe(viewLifecycleOwner) { updateUiCountrySpinner(it) }
        viewModel.listSectorTypeResult.observe(viewLifecycleOwner) { updateUiSectorSpinner(it) }
    }

    private fun updateUiCountrySpinner(countries: List<JittaCountry>) {
        val newAdapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            countries.map { it.name }
        )
        binding.spCountry.adapter = newAdapter
        val index = countries.indexOfFirst { it.name.lowercase() == "Thailand".lowercase() }
        if (index != -1) binding.spCountry.setSelection(index)
    }

    private fun updateUiSectorSpinner(sectors: List<JittaSectorType>) {
        val list = mutableListOf("All sectors")
        list.addAll(sectors.map { it.name })

        val newAdapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            list
        )
        binding.spFilter.adapter = newAdapter
        binding.spFilter.setSelection(0)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        p0?.let { nonNullAdapter ->
            when (nonNullAdapter.id) {
                binding.spFilter.id -> {}
                binding.spCountry.id -> {
                    val selectedCountry = nonNullAdapter.getItemAtPosition(p2) as String //TEMP
                    viewModel.refresh(market = "th")
                }

                else -> Unit
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}