package com.mamafarm.android.ranking.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamafarm.android.network.repository.CountryApi
import com.mamafarm.android.network.repository.SectorApi
import com.mamafarm.android.network.response.BaseResponse
import com.mamafarm.android.ranking.model.JittaCountry
import com.mamafarm.android.ranking.model.JittaCountryMapper
import com.mamafarm.android.ranking.model.JittaSectorType
import com.mamafarm.android.ranking.model.JittaSectorTypeMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JittaRankingViewModel @Inject constructor(
    private val countryRepository: CountryApi.Impl,
    private val sectorRepository: SectorApi.Impl,
    private val countryMapper: JittaCountryMapper,
    private val sectorTypeMapper: JittaSectorTypeMapper,
) : ViewModel() {

    private val _countriesResult by lazy { MutableLiveData<List<JittaCountry>>() }
    val countriesResult: LiveData<List<JittaCountry>> = _countriesResult

    private val _listSectorTypeResult by lazy { MutableLiveData<List<JittaSectorType>>() }
    val listSectorTypeResult: LiveData<List<JittaSectorType>> = _listSectorTypeResult

    fun getAvailableCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = countryRepository.queryAvailableCountries()) {
                is BaseResponse.Error -> Unit
                is BaseResponse.Success -> {
                    _countriesResult.postValue(result.data.map { countryMapper.map(it) })
                }
            }
        }
    }

    fun getListSectorType() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = sectorRepository.querySectors()) {
                is BaseResponse.Error -> Unit
                is BaseResponse.Success -> {
                    _listSectorTypeResult.postValue(result.data.map { sectorTypeMapper.map(it) })
                }
            }
        }
    }
}