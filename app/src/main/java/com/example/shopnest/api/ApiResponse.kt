package com.example.shopnest.api

sealed interface ApiResponse<out T> {
    data object Loading : ApiResponse<Nothing>
    data class Success<out T>(val data: T, val code: Int) : ApiResponse<T>
    data class HttpError(val code: Int, val message: String?, val rawBody: String?) : ApiResponse<Nothing>
    data class NetworkError(val throwable: Throwable) : ApiResponse<Nothing>
    data object Canceled : ApiResponse<Nothing>
}