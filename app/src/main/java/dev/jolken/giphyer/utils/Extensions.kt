package dev.jolken.giphyer.utils

import dev.jolken.giphyer.models.responses.BaseGiphyResponse
import org.json.JSONObject
import retrofit2.Response

fun <T> Response<T>.toKotlinError(): Error?{
    if (this.body() == null) {
        return Error("Empty response body")
    }
    when (val body = this.body()) {
        is BaseGiphyResponse -> return Error(body.meta.message)
    }
}