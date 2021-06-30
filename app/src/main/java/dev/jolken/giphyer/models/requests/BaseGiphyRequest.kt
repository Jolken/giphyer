package dev.jolken.giphyer.models.requests

import com.google.gson.annotations.SerializedName

interface BaseGiphyRequest {
    val apiKey: String
}