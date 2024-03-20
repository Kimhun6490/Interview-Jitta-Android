package com.mamafarm.android.stockdetails.model

data class JittaStockDetail(
    val stockName: String,
    val stockCode: String,
    val isFollowing: Boolean,
    val company: JittaCompany
)