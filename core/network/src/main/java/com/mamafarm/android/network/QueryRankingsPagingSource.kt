package com.mamafarm.android.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mamafarm.android.network.data.ranking.QueryRankingResponse
import com.mamafarm.android.network.repository.RankingApi
import com.mamafarm.android.network.response.BaseResponse
import javax.inject.Inject

class QueryRankingsPagingSource @Inject constructor(private val repository: RankingApi.Impl) :
    PagingSource<Int, QueryRankingResponse>() {
    override fun getRefreshKey(state: PagingState<Int, QueryRankingResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QueryRankingResponse> {
        return try {
            val offset = params.key ?: 0
            val limit = 10
            return when (val response = repository.queryRankings("th", offset, limit)) {
                is BaseResponse.Error -> LoadResult.Error(IllegalStateException())
                is BaseResponse.Success -> {
                    val list = response.data.data
                    val nextKey = if (list.isEmpty()) null else offset + 10
                    LoadResult.Page(list, null, nextKey)
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}