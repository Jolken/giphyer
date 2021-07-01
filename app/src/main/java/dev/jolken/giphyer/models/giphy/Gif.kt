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
    val images: Images
) {
    companion object {
        fun GetFakeGif() : Gif{
            return Gif(
                "gif",
                Random.nextInt().toString(),
                "",
                "https://giphy.com/gifs/artestpage-milogreenelietome-5yLgoclXrPGJ42JqEBG",
                "",
                Images(
                    PreviewGifImage(
                        "https://giphy.com/gifs/artestpage-milogreenelietome-5yLgoclXrPGJ42JqEBG",
                        Random.nextInt(100, 200).toString(),
                        Random.nextInt(100, 200).toString(),
                    ),
                    OriginalImage(
                        Random.nextInt(100, 200).toString(),
                        Random.nextInt(100, 200).toString(),
                        "100",
                        "15",
                        "",
                        "",
                        "",
                        ""

                    ),
                    DownsizedSmallImage(
                        "https://giphy.com/gifs/artestpage-milogreenelietome-5yLgoclXrPGJ42JqEBG",
                        Random.nextInt(100, 200).toString(),
                        Random.nextInt(100, 200).toString(),
                        ""
                    )
                )
            )
        }
    }
}