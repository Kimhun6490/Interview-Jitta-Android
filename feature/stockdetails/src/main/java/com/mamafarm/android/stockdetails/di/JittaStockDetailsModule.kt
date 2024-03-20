package com.mamafarm.android.stockdetails.di

import com.mamafarm.android.stockdetails.model.JittaStockDetailMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class JittaStockDetailsModule {

    @Provides
    fun providesJittaStockDetailMapper() = JittaStockDetailMapper()
}