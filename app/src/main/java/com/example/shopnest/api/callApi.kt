package com.example.shopnest.api

import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T : Any> callApi(
    apiRequest: suspend () -> Response<T>,
    errorParser: (okhttp3.ResponseBody?) -> Pair<String?, String?> = { rb ->
        ErrorParser.parseMessage(rb) to ErrorParser.rawTruncated(rb)
    }
): ApiResponse<T> {
    return try {
        val response = apiRequest()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                ApiResponse.Success(body, response.code())
            } else {
                ApiResponse.HttpError(response.code(), "Empty response body", null)
            }
        } else {
            val (msg, raw) = errorParser(response.errorBody())
            ApiResponse.HttpError(response.code(), msg ?: response.message(), raw)
        }
    } catch (ce: CancellationException) {
        ApiResponse.Canceled
    } catch (he: HttpException) {
        ApiResponse.HttpError(he.code(), he.message(), null)
    } catch (io: IOException) {
        ApiResponse.NetworkError(io)
    } catch (t: Throwable) {
        ApiResponse.NetworkError(t)
    }
}