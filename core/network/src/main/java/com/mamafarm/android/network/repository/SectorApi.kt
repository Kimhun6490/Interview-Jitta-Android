package com.mamafarm.android.network.repository

import com.apollographql.apollo3.ApolloClient
import com.mamafarm.android.network.SectorsQuery
import com.mamafarm.android.network.data.sector.QuerySectorResponse
import com.mamafarm.android.network.response.BaseResponse
import javax.inject.Inject

interface SectorApi {

    suspend fun querySectors(): BaseResponse<List<QuerySectorResponse>>

    class Impl @Inject constructor(private val client: ApolloClient) : SectorApi {

        override suspend fun querySectors(): BaseResponse<List<QuerySectorResponse>> {
            return try {
                val query = SectorsQuery()
                val response = client.query(query).execute()
                val listJittaSectorType = response.data?.listJittaSectorType
                    ?: return BaseResponse.Error(IllegalStateException())
                val sectors = listJittaSectorType
                    .mapNotNull { it }
                    .map { QuerySectorResponse(it.id, it.name) }

                if (sectors.isEmpty()) BaseResponse.Error(IllegalStateException())
                else BaseResponse.Success(sectors)
            } catch (ex: Exception) {
                BaseResponse.Error(ex)
            }
        }
    }
}