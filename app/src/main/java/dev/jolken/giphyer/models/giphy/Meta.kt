package dev.jolken.giphyer.models.giphy

import com.google.gson.annotations.SerializedName

data class Meta (
    @SerializedName("msg")
    val message:String,
    val status: Int,
    @SerializedName("response_id")
    val responseId: String
    )