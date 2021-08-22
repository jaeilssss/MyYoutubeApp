package com.example.myyoutubeapp.model


import com.google.gson.annotations.SerializedName

//data class VideoModel(
//    @SerializedName("description")
//    val description: String,
//    @SerializedName("sources")
//    val sources: String,
//    @SerializedName("subtitle")
//    val subtitle: String,
//    @SerializedName("thumb")
//    val thumb: String,
//    @SerializedName("title")
//    val title: String
//)

data class VideoModel(
        val title: String,
        val sources: String,
        val subtitle: String,
        val thumb: String,
        val description: String
)