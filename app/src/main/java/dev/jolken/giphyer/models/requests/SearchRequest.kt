package dev.jolken.giphyer.models.requests

import com.google.gson.annotations.SerializedName

data class SearchRequest(
    @SerializedName("api_key") override val apiKey: String,
    @SerializedName("q")  val query: String,
    val limit: Int,
    val offset: Int
): BaseGiphyRequest

