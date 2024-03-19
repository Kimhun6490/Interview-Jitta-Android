package com.mamafarm.android.ranking.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mamafarm.android.network.QueryRankingsPagingSource
import com.mamafarm.android.network.data.ranking.QueryRankingResponse
import com.mamafarm.android.network.repository.CountryApi
import com.mamafarm.android.network.repository.RankingApi
import com.mamafarm.android.network.repository.SectorApi
import com.mamafarm.android.network.response.BaseResponse
import com.mamafarm.android.ranking.model.JittaCountry
import com.mamafarm.android.ranking.model.JittaCountryMapper
import com.mamafarm.android.ranking.model.JittaRank
import com.mamafarm.android.ranking.model.JittaRankMapper
import com.mamafarm.android.ranking.model.JittaSectorType
import com.mamafarm.android.ranking.model.JittaSectorTypeMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JittaRankingViewModel @Inject constructor(
    private val countryRepository: CountryApi.Impl,
    private val sectorRepository: SectorApi.Impl,
    private val rankingRepository: RankingApi.Impl,
    private val countryMapper: JittaCountryMapper,
    private val sectorTypeMapper: JittaSectorTypeMapper,
    private val rankMapper: JittaRankMapper,
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

    fun getRankingList(): Flow<PagingData<JittaRank>> {
        return Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            )
        ) { QueryRankingsPagingSource(rankingRepository) }
            .flow
            .cachedIn(viewModelScope)
            .map { pagingData ->
                pagingData.map { rankMapper.map(it) }
            }
    }
}