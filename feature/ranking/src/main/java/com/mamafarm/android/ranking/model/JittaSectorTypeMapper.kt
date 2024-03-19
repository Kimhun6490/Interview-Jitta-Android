package com.mamafarm.android.ranking.model

import com.mamafarm.android.common.mapper.Mapper
import com.mamafarm.android.network.data.sector.QuerySectorResponse

class JittaSectorTypeMapper : Mapper<QuerySectorResponse, JittaSectorType> {

    override suspend fun map(from: QuerySectorResponse): JittaSectorType {
        return JittaSectorType(
            name = from.name ?: "",
            id = from.id,
        )
    }
}
