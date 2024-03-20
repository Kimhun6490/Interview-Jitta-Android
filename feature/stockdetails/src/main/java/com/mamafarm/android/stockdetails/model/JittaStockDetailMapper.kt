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
        return JittaStockDetail(
            stockName = from.title ?: "",
            stockCode = from.id,
            isFollowing = from.isFollowing ?: false,
            company = company
        )
    }
}