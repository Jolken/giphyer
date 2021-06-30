package dev.jolken.giphyer.models.requests

import com.google.gson.annotations.SerializedName

data class GetGifByIdRequest(
    @SerializedName("api_key") override val apiKey: String,
    @SerializedName("gif_id") val gifId: String,
) : BaseGiphyRequest