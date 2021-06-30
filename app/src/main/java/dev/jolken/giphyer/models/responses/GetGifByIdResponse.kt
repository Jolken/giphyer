package dev.jolken.giphyer.models.responses

import dev.jolken.giphyer.models.giphy.Gif
import dev.jolken.giphyer.models.giphy.Meta

data class GetGifByIdResponse(val data: Gif, override val meta: Meta) : BaseGiphyResponse