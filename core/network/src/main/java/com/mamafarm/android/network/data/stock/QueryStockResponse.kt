package com.mamafarm.android.network.data.stock

import com.mamafarm.android.network.data.company.QueryCompanyResponse
import com.mamafarm.android.network.data.sector.QuerySectorResponse

data class QueryStockResponse(
    val title: String?,
    val id: String,
    val stockId: Int,
    val isFollowing: Boolean?,
    val sector: QuerySectorResponse?,
    val industry: String?,
    val company: QueryCompanyResponse?,
    val jitta: QueryJittaResponse?
)
