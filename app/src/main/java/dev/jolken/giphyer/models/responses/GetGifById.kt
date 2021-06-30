package dev.jolken.giphyer.models.responses

import dev.jolken.giphyer.models.giphy.Gif
import dev.jolken.giphyer.models.giphy.Meta

data class GetGifById(val data: Gif, val meta: Meta)