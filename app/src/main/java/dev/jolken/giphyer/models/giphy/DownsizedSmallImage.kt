package dev.jolken.giphyer.models.giphy

import com.google.gson.annotations.SerializedName

data class DownsizedSmallImage(
    val mp4: String,
    val width: String,
    val height: String,
    @SerializedName("mp4_size")
    val mp4Size: String
)