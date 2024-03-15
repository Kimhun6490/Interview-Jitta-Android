package com.mamafarm.android.market.presentation

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

class JittaMarketFragment : Fragment(), AppBarLayout.OnOffsetChangedListener,
    AdapterView.OnItemSelectedListener {
    private lateinit var binding: JittaFragmentMarketBinding
    private val viewModel: JittaMarketViewModel by viewModels()

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
        val countries = arrayOf("Java", "PHP", "Kotlin", "Javascript", "Python", "Swift")
        val countryAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, countries)
        countryAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        with(binding.spCountry) {
            adapter = countryAdapter
            setSelection(0, false)
            onItemSelectedListener = this@JittaMarketFragment
            layoutParams = params
            setPopupBackgroundResource(R.color.background_light)
        }
    }

    private fun setupRecycleView() {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}