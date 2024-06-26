package com.mamafarm.android.network.data.ranking

import com.mamafarm.android.network.data.sector.QuerySectorResponse

data class QueryRankingResponse(
    val id: String,
    val name: String?,
    val sector: QuerySectorResponse?,
    val rank: Int?,
    val total: Int?,
    val stockId: Int?
)