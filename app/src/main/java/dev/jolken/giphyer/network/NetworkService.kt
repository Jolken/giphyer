package dev.jolken.giphyer.network

import okhttp3.Interceptor
import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private const val BASE_URL = "https://api.giphy.com/v1/gifs/"
//    private const val BASE_URL = "https://webhook.site/25bbca29-4259-4228-80f3-43240e90eecf/"

    private val baseInterceptor: Interceptor = invoke { chain ->
        val newUrl = chain
            .request()
            .url
            .newBuilder()
            .build()

        val request = chain
            .request()
            .newBuilder()
            .url(newUrl)
            .build()

        return@invoke chain.proceed(request)
    }

    private val client: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(baseInterceptor)
        .build()

    fun retrofitService(): GiphyApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(GiphyApi::class.java)
    }
}