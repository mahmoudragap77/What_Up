package com.nawa.whatup

import com.google.gson.annotations.SerializedName

data class News(

    val status: String,
    val totalResults: Int,
    val articles: ArrayList<Articles>
)
data class Articles(
    val title: String,
    val url: String,
    val urlToImage: String
)
