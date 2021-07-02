package dev.jolken.giphyer.models.giphy

import com.google.gson.annotations.SerializedName

data class Downsized(
    val url: String,
    val width: String,
    val height: String
)