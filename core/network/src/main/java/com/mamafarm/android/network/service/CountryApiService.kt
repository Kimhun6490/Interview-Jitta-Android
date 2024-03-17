package com.mamafarm.android.network.service

import com.apollographql.apollo3.ApolloClient

interface CountryApiService {

    suspend fun queryCountry()

    class Impl(private val client: ApolloClient) : CountryApiService {
        override suspend fun queryCountry() {

        }
    }
}