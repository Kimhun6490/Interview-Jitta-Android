package com.mamafarm.android.ranking.presentation

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.AppBarLayout
import com.mamafarm.android.market.databinding.JittaFragmentMarketBinding
import com.mamafarm.android.ranking.model.JittaCountry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JittaRankingFragment : Fragment(), AppBarLayout.OnOffsetChangedListener,
    AdapterView.OnItemSelectedListener {
    private lateinit var binding: JittaFragmentMarketBinding
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
        val countryAdapter = ArrayAdapter(
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
    }

    private fun setupRecycleView() {

    }

    private fun loadData() {
        viewModel.getAvailableCountries()
        viewModel.countriesResult.observe(viewLifecycleOwner) { updateUiSpinner(it) }
    }

    private fun updateUiSpinner(countries: List<JittaCountry>) {
        val newCountryAdapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            countries.map { it.name }
        )
        binding.spCountry.adapter = newCountryAdapter
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}