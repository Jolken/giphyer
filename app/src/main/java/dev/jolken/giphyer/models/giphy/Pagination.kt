package dev.jolken.giphyer.models.giphy

data class Pagination(
    val offset: Int,
    val totalCount: Int,
    val count: Int
)