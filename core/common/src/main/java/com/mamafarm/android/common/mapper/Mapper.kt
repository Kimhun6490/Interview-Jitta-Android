package com.mamafarm.android.common.mapper

interface Mapper<F, T> {

    suspend fun map(from: F): T
}