package dev.jolken.giphyer.models.giphy

import com.google.gson.annotations.SerializedName

data class OriginalImage(
    val width: String,
    val height: String,
    val size: String,
    val frames: String,
    val mp4: String,
    @SerializedName("mp4_size")
    val mp4Size: String,
    val webp: String,
    @SerializedName("webp_size")
    val webpSize: String
)