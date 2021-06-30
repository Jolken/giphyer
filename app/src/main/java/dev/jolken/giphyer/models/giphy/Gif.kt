package dev.jolken.giphyer.models.giphy

import com.google.gson.annotations.SerializedName

data class Gif(
    val type: String = "gif",
    val id: String,
    val slug: String,
    val url: String,
    @SerializedName("bitly_url")
    val bitlyUrl: String,
    val images: Images
)