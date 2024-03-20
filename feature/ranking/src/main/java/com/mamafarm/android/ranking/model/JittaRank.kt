package com.mamafarm.android.ranking.model

data class JittaRank(
    val id: String,
    val stockId: Int,
    val title: String,
    val rank: Int,
    val total: Int
)