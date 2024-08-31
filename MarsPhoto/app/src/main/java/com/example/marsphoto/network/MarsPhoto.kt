package com.example.marsphoto.network

import com.google.gson.annotations.SerializedName

data class MarsPhoto(
    @SerializedName("id") val id: String,
    @SerializedName("img_src") val imgSrc: String
)
