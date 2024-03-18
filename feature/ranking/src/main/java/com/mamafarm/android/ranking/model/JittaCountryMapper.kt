package com.mamafarm.android.ranking.model

import com.mamafarm.android.common.mapper.Mapper
import com.mamafarm.android.network.data.QueryAvailableCountryResponse

class JittaCountryMapper : Mapper<QueryAvailableCountryResponse, JittaCountry> {

    override suspend fun map(from: QueryAvailableCountryResponse): JittaCountry {
        return JittaCountry(
            name = from.name ?: "",
            flag = from.flag ?: "",
        )
    }
}
