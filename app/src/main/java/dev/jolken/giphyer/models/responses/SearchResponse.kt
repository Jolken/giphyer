package dev.jolken.giphyer.models.responses

import dev.jolken.giphyer.models.giphy.Gif
import dev.jolken.giphyer.models.giphy.Meta
import dev.jolken.giphyer.models.giphy.Pagination

data class SearchResponse(override val data: Array<Gif>, val pagination: Pagination, override val meta: Meta): BaseGiphyResponseWithGifs
