package com.mamafarm.android.stockdetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mamafarm.android.stockdetails.databinding.JittaFragmentStockDetailsBinding

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
}