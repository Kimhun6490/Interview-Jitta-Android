package com.mamafarm.android.ranking.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamafarm.android.network.repository.CountryApi
import com.mamafarm.android.network.response.BaseResponse
import com.mamafarm.android.ranking.model.JittaCountry
import com.mamafarm.android.ranking.model.JittaCountryMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JittaRankingViewModel @Inject constructor(
    private val repository: CountryApi.Impl,
    private val countryMapper: JittaCountryMapper,
) : ViewModel() {

    private val _countriesResult by lazy { MutableLiveData<List<JittaCountry>>() }
    val countriesResult: LiveData<List<JittaCountry>> = _countriesResult

    fun getAvailableCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.queryAvailableCountry()) {
                is BaseResponse.Error -> Unit
                is BaseResponse.Success -> {
                    _countriesResult.postValue(result.data.map { countryMapper.map(it) })
                }
            }
        }
    }
}