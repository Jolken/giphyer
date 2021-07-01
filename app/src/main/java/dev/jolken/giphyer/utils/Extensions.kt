package dev.jolken.giphyer.utils

import dev.jolken.giphyer.models.responses.BaseGiphyResponse
import retrofit2.Response

fun <T> Response<T>.toKotlinError(): Error {
    when (val body = this.body()) {
        null -> return Error("Empty response body")
        is BaseGiphyResponse -> return Error(body.meta.message)
    }
    return Error("Unexpected error")
}