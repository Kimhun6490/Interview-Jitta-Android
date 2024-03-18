package com.mamafarm.android.network.repository

import com.apollographql.apollo3.ApolloClient
import com.mamafarm.android.network.CountryWithNameAndFlagQuery
import com.mamafarm.android.network.data.QueryAvailableCountryResponse
import com.mamafarm.android.network.response.BaseResponse
import javax.inject.Inject

interface CountryApi {

    suspend fun queryAvailableCountries(): BaseResponse<List<QueryAvailableCountryResponse>>

    class Impl @Inject constructor(private val client: ApolloClient) : CountryApi {

        override suspend fun queryAvailableCountries(): BaseResponse<List<QueryAvailableCountryResponse>> {
            return try {
                val query = CountryWithNameAndFlagQuery()
                val response = client.query(query).execute()
                val availableCountry = response.data?.availableCountry
                val countries = availableCountry?.mapNotNull { it }?.map {
                    QueryAvailableCountryResponse(name = it.name, flag = it.flag)
                }

                if (countries.isNullOrEmpty()) BaseResponse.Error(IllegalStateException())
                else BaseResponse.Success(countries)
            } catch (ex: Exception) {
                BaseResponse.Error(ex)
            }
        }
    }
}