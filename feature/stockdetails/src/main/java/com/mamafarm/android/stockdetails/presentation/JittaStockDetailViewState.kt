package com.mamafarm.android.stockdetails.presentation

import com.mamafarm.android.stockdetails.model.JittaStockDetail

sealed class JittaStockDetailViewState {
    data object Loading : JittaStockDetailViewState()
    data object Error : JittaStockDetailViewState()
    data class Content(val stockDetail: JittaStockDetail) : JittaStockDetailViewState()
}