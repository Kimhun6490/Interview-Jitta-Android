package com.mamafarm.android.ranking.model

import com.mamafarm.android.common.mapper.Mapper
import com.mamafarm.android.network.data.country.QueryAvailableCountryResponse

class JittaCountryMapper : Mapper<QueryAvailableCountryResponse, JittaCountry> {

    override suspend fun map(from: QueryAvailableCountryResponse): JittaCountry {
        return JittaCountry(
            code = from.code ?: "",
            name = from.name ?: "",
            flag = from.flag ?: "",
        )
    }
}
