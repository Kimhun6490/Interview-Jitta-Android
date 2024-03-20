package com.mamafarm.android.stockdetails.model

import com.mamafarm.android.common.mapper.Mapper
import com.mamafarm.android.network.data.stock.QueryStockResponse

class JittaStockDetailMapper : Mapper<QueryStockResponse, JittaStockDetail> {

    override suspend fun map(from: QueryStockResponse): JittaStockDetail {
        val company = JittaCompany(
            description = "",
            sector = from.sector?.name ?: "",
            industry = from.industry ?: "",
            url = from.company?.link?.get(0) ?: ""
        )
        val factors = from.jitta?.factor?.values?.get(0)?.value?.let {
            val f1 = it.growth.let { f -> JittaFactor(f?.name ?: "", f?.value?.toInt() ?: -1) }
            val f2 = it.recent.let { f -> JittaFactor(f?.name ?: "", f?.value?.toInt() ?: -1) }
            val f3 = it.financial.let { f -> JittaFactor(f?.name ?: "", f?.value?.toInt() ?: -1) }
            val f4 = it.returned.let { f -> JittaFactor(f?.name ?: "", f?.value?.toInt() ?: -1) }
            val f5 = it.management.let { f -> JittaFactor(f?.name ?: "", f?.value?.toInt() ?: -1) }
            listOf(f1, f2, f3, f4, f5)
        }
        return JittaStockDetail(
            stockName = from.title ?: "",
            stockCode = from.id,
            isFollowing = from.isFollowing ?: false,
            company = company,
            factors = factors ?: emptyList()
        )
    }
}