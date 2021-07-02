package dev.jolken.giphyer.models.giphy

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("preview_gif")
    val previewGif: PreviewGifImage,
    val original: OriginalImage,
    @SerializedName("downsized")
    val downsized: Downsized
)