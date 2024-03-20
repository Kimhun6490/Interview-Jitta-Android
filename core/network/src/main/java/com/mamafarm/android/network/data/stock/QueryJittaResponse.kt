package com.mamafarm.android.network.data.stock

data class QueryJittaResponse(
    val factor: QueryJittaFactor
)

data class QueryJittaFactor(
    val values: List<QueryFactorValue>
)

data class QueryFactorValue(
    val id: String?,
    val value: QueryFactorDetails
)

data class QueryFactorDetails(
    val growth: QueryFactorDetail?,
    val financial: QueryFactorDetail?,
    val returned: QueryFactorDetail?,
    val management: QueryFactorDetail?,
    val recent: QueryFactorDetail?
)

data class QueryFactorDetail(
    val name: String?,
    val value: Double?
)