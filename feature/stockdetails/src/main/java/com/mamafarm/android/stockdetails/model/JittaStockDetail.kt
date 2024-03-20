package com.mamafarm.android.stockdetails.model

data class JittaStockDetail(
    val stockName: String,
    val stockCode: String,
    var isFollowing: Boolean,
    val company: JittaCompany,
    val factors: List<JittaFactor>
)