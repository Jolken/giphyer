package dev.jolken.giphyer.models.giphy

data class Images(
    val preview_gif: PreviewGifImage,
    val original: OriginalImage,
    val downsized_small: DownsizedSmallImage
)