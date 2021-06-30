package dev.jolken.giphyer.models.requests

import com.google.gson.annotations.SerializedName

data class TrendingRequest(
    @SerializedName("api_key") override val apiKey: String,
    val limit: Int,
    val offset: Int

): BaseGiphyRequest