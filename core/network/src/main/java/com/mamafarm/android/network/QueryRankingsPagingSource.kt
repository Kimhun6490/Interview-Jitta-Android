package com.mamafarm.android.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mamafarm.android.network.data.ranking.QueryRankingResponse
import com.mamafarm.android.network.repository.RankingApi
import com.mamafarm.android.network.request.ranking.QueryRankingParamsRequest
import com.mamafarm.android.network.response.BaseResponse
import javax.inject.Inject

class QueryRankingsPagingSource @Inject constructor(
    private val repository: RankingApi.Impl,
    private val query: QueryRankingParamsRequest
) :
    PagingSource<Int, QueryRankingResponse>() {
    override fun getRefreshKey(state: PagingState<Int, QueryRankingResponse>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QueryRankingResponse> {
        return try {
            val page = params.key ?: 0
            return when (val response =
                repository.queryRankings(query.market, query.sector, page, 10)) {
                is BaseResponse.Error -> LoadResult.Error(IllegalStateException())
                is BaseResponse.Success -> {
                    val list = response.data.data
                    val nextKey = if (list.isEmpty()) null else page + 1
                    LoadResult.Page(list, null, nextKey)
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}