package dev.jolken.giphyer.models.requests

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class SearchRequest(
    @SerializedName("api_key") override val apiKey: String,
    @SerializedName("q")  val query: String,
    val limit: Int,
    val offset: Int
): BaseGiphyRequest {
    fun toMap(): Map<String, String> {
        return mapOf("api_key" to apiKey, "q" to query, "limit" to limit.toString(), "offset" to offset.toString())

    }
}

