package dev.jolken.giphyer.models.giphy

data class Gif(
    val type: String = "gif",
    val id: String,
    val slug: String,
    val url: String,
    val bitly_url: String,
    val images: Images
)