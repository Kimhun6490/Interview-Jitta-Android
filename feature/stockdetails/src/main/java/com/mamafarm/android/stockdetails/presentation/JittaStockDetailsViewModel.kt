package com.mamafarm.android.stockdetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamafarm.android.network.repository.StockApi
import com.mamafarm.android.network.request.stock.QueryStockParamsRequest
import com.mamafarm.android.network.response.BaseResponse
import com.mamafarm.android.stockdetails.model.JittaStockDetailMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JittaStockDetailsViewModel @Inject constructor(
    private val stockRepository: StockApi.Impl,
    private val stockDetailMapper: JittaStockDetailMapper
) : ViewModel() {

    private val _viewState = MutableLiveData<JittaStockDetailViewState>()
    val viewState: LiveData<JittaStockDetailViewState> get() = _viewState

    fun getStockDetail(id: String, stockId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val query = QueryStockParamsRequest(id, stockId)
            when (val result = stockRepository.queryStockById(query)) {
                is BaseResponse.Error -> _viewState.postValue(JittaStockDetailViewState.Error)
                is BaseResponse.Success -> _viewState.postValue(
                    JittaStockDetailViewState.Content(
                        stockDetailMapper.map(result.data)
                    )
                )
            }
        }
    }
}