package com.mamafarm.android.ranking.di

import com.mamafarm.android.ranking.model.JittaCountryMapper
import com.mamafarm.android.ranking.model.JittaSectorTypeMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class JittaRankingModule {

    @Provides
    fun providesJittaCountryMapper() = JittaCountryMapper()

    @Provides
    fun providesJittaSectorTypeMapper() = JittaSectorTypeMapper()
}