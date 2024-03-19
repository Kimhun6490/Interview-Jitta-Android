package com.mamafarm.android.network.repository

import com.apollographql.apollo3.ApolloClient
import com.mamafarm.android.network.CountriesQuery
import com.mamafarm.android.network.data.country.QueryAvailableCountryResponse
import com.mamafarm.android.network.response.BaseResponse
import javax.inject.Inject

interface CountryApi {

    suspend fun queryAvailableCountries(): BaseResponse<List<QueryAvailableCountryResponse>>

    class Impl @Inject constructor(private val client: ApolloClient) : CountryApi {

        override suspend fun queryAvailableCountries(): BaseResponse<List<QueryAvailableCountryResponse>> {
            return try {
                val query = CountriesQuery()
                val response = client.query(query).execute()
                val availableCountry = response.data?.availableCountry
                    ?: return BaseResponse.Error(IllegalStateException())
                val countries = availableCountry
                    .mapNotNull { it }
                    .map { QueryAvailableCountryResponse(it.name, it.flag) }

                if (countries.isEmpty()) BaseResponse.Error(IllegalStateException())
                else BaseResponse.Success(countries)
            } catch (ex: Exception) {
                BaseResponse.Error(ex)
            }
        }
    }
}