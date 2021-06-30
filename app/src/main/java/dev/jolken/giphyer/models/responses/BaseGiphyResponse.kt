package dev.jolken.giphyer.models.responses

import dev.jolken.giphyer.models.giphy.Meta

interface BaseGiphyResponse {
    val meta: Meta
}