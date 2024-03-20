package com.mamafarm.android.network.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.mamafarm.android.network.RankingQuery
import com.mamafarm.android.network.data.ranking.QueryRankingListResponse
import com.mamafarm.android.network.data.ranking.QueryRankingResponse
import com.mamafarm.android.network.data.sector.QuerySectorResponse
import com.mamafarm.android.network.response.BaseResponse
import javax.inject.Inject

interface RankingApi {

    suspend fun queryRankings(
        market: String,
        page: Int?,
        limit: Int?
    ): BaseResponse<QueryRankingListResponse>

    class Impl @Inject constructor(private val client: ApolloClient) : RankingApi {

        override suspend fun queryRankings(
            market: String,
            page: Int?,
            limit: Int?
        ): BaseResponse<QueryRankingListResponse> {
            return try {
                val query = RankingQuery(market, Optional.present(page), Optional.present(limit))
                val response = client.query(query).execute()
                val jittaRanking = response.data?.jittaRanking
                    ?: return BaseResponse.Error(IllegalStateException())

                val count = jittaRanking.count ?: return BaseResponse.Error(IllegalStateException())
                val data = jittaRanking.data ?: return BaseResponse.Error(IllegalStateException())

                val ranking = data
                    .mapNotNull { it }
                    .map {
                        val sector = it.sector!!
                        val sectorRes = QuerySectorResponse(sector.id, sector.name)
                        QueryRankingResponse(
                            it.id!!,
                            it.name,
                            sectorRes,
                            it.rank,
                            count,
                            it.stockId
                        )
                    }

                val list = QueryRankingListResponse(count, ranking)
                BaseResponse.Success(list)
            } catch (ex: Exception) {
                BaseResponse.Error(ex)
            }
        }
    }
}