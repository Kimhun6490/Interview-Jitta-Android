package com.mamafarm.android.ranking.model

import com.mamafarm.android.common.mapper.Mapper
import com.mamafarm.android.network.data.ranking.QueryRankingResponse

class JittaRankMapper : Mapper<QueryRankingResponse, JittaRank> {

    override suspend fun map(from: QueryRankingResponse): JittaRank {
        return JittaRank(
            title = from.name ?: "",
            id = from.id,
            rank = from.rank ?: -1,
            total = from.total ?: -1,
            stockId = from.stockId ?: -1
        )
    }
}
