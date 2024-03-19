package com.mamafarm.android.ranking.model

import com.mamafarm.android.common.mapper.Mapper
import com.mamafarm.android.network.data.ranking.QueryRankingListResponse
import com.mamafarm.android.network.data.ranking.QueryRankingResponse
import com.mamafarm.android.network.data.sector.QuerySectorResponse

class JittaRankMapper : Mapper<QueryRankingResponse, JittaRank> {

    override suspend fun map(from: QueryRankingResponse): JittaRank {
        return JittaRank(
            title = from.name ?: "",
            id = from.id,
            code = "code",
            rank = from.rank ?: -1
        )
    }
}
