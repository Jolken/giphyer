package dev.jolken.giphyer.models.responses

import dev.jolken.giphyer.models.giphy.Gif

interface BaseGiphyResponseWithGifs:BaseGiphyResponse {
    val data: Array<Gif>
}