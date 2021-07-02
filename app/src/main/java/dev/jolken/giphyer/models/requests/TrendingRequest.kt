package dev.jolken.giphyer.models.requests

import com.google.gson.annotations.SerializedName

data class TrendingRequest(
    @SerializedName("api_key") override val apiKey: String,
    val limit: Int,
    val offset: Int

): BaseGiphyRequest {
    fun toMap(): Map<String, String> {
        return mapOf("api_key" to apiKey, "limit" to limit.toString(), "offset" to offset.toString())
    }
}