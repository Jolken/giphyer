package dev.jolken.giphyer.network

import dev.jolken.giphyer.models.requests.GetGifByIdRequest
import dev.jolken.giphyer.models.requests.SearchRequest
import dev.jolken.giphyer.models.requests.TrendingRequest
import dev.jolken.giphyer.models.responses.SearchResponse
import dev.jolken.giphyer.models.responses.TrendingResponse
import dev.jolken.giphyer.models.responses.GetGifByIdResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path

interface GiphyApi {
    @FormUrlEncoded
    @GET("search")
    suspend fun getSearch(@Body body: SearchRequest): Response<SearchResponse>

    @FormUrlEncoded
    @GET("trending")
    suspend fun getTrending(@Body body: TrendingRequest):Response<TrendingResponse>

    @FormUrlEncoded
    @GET("{id}}")
    suspend fun getGifById(@Path("id") id: String, @Body body: GetGifByIdRequest):Response<GetGifByIdResponse>
}