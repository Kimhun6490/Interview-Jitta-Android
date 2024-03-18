package com.mamafarm.android.network.response

sealed class BaseResponse<out T> {
    data class Error(val exception: Exception) : BaseResponse<Nothing>()
    data class Success<T>(val data: T) : BaseResponse<T>()
}