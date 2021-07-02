package dev.jolken.giphyer.models.giphy

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class Gif(

    val type: String = "gif",
    val id: String,
    val slug: String,
    val url: String,
    @SerializedName("bitly_url")
    val bitlyUrl: String,
    val images: Images,
    val title: String
) {
    companion object {
        fun GetFakeGif() : Gif{
            return Gif(
                "gif",
                Random.nextInt().toString(),
                "",
                "https://giphy.com/gifs/artestpage-milogreenelietome-5yLgoclXrPGJ42JqEBG",
                "https://giphy.com/gifs/artestpage-milogreenelietome-5yLgoclXrPGJ42JqEBG",
                Images(
                    PreviewGifImage(
                        "https://giphy.com/gifs/artestpage-milogreenelietome-5yLgoclXrPGJ42JqEBG",
                        Random.nextInt(100, 200).toString(),
                        Random.nextInt(100, 200).toString(),
                    ),
                    OriginalImage(
                        Random.nextInt(300, 500).toString(),
                        Random.nextInt(300, 500).toString(),
                        "300",
                        "15",
                        "",
                        "",
                        "",
                        ""

                    ),
                    Downsized(
                        "https://giphy.com/gifs/artestpage-milogreenelietome-5yLgoclXrPGJ42JqEBG",
                        Random.nextInt(300, 500).toString(),
                        Random.nextInt(300, 500).toString()
                    )
                ),
                "ExampleTitle ${Random.nextInt()}"
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        when(other) {
            is Gif -> return other.id == this.id
        }
        return false
    }
}