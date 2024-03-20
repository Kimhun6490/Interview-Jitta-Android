package com.mamafarm.android.network.repository

import com.apollographql.apollo3.ApolloClient
import com.mamafarm.android.network.SectorsQuery
import com.mamafarm.android.network.StockQuery
import com.mamafarm.android.network.data.company.QueryCompanyResponse
import com.mamafarm.android.network.data.sector.QuerySectorResponse
import com.mamafarm.android.network.data.stock.QueryStockResponse
import com.mamafarm.android.network.request.stock.QueryStockParamsRequest
import com.mamafarm.android.network.response.BaseResponse
import javax.inject.Inject

interface StockApi {

    suspend fun queryStockById(params: QueryStockParamsRequest): BaseResponse<QueryStockResponse>

    class Impl @Inject constructor(private val client: ApolloClient) : StockApi {

        override suspend fun queryStockById(params: QueryStockParamsRequest): BaseResponse<QueryStockResponse> {
            return try {
                val query = StockQuery(params.id, params.stockId)
                val response = client.query(query).execute()
                val stock = response.data?.stock?.let { s ->
                    QueryStockResponse(
                        title = s.title,
                        id = params.id,
                        stockId = params.stockId,
                        isFollowing = s.isFollowing,
                        sector = s.sector?.let { QuerySectorResponse(name = it.name, id = it.id) },
                        industry = s.industry,
                        company = s.company?.let { c -> QueryCompanyResponse(link = c.link?.map { it?.url }) }
                    )
                } ?: return BaseResponse.Error(IllegalStateException())

                BaseResponse.Success(stock)
            } catch (ex: Exception) {
                BaseResponse.Error(ex)
            }
        }
    }
}