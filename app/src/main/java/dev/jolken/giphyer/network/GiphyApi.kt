package dev.jolken.giphyer.network

import dev.jolken.giphyer.models.requests.GetGifByIdRequest
import dev.jolken.giphyer.models.requests.SearchRequest
import dev.jolken.giphyer.models.requests.TrendingRequest
import dev.jolken.giphyer.models.responses.SearchResponse
import dev.jolken.giphyer.models.responses.TrendingResponse
import dev.jolken.giphyer.models.responses.GetGifByIdResponse
import retrofit2.Response
import retrofit2.http.*

interface GiphyApi {
    @GET("search")
    suspend fun getSearch(@QueryMap body: Map<String,String>): Response<SearchResponse>

    @GET("trending")
    suspend fun getTrending(@QueryMap body: Map<String,String>):Response<TrendingResponse>

    @GET("{id}}")
    suspend fun getGifById(@Path("id") id: String, @Body body: GetGifByIdRequest):Response<GetGifByIdResponse>
}