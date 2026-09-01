package com.nawa.whatup

import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {

    @GET("/v2/top-headlines?country=us&category=general&apiKey=8961fc799fe94e248d5966e356cc6394&pageSize=30")
    fun getNews() : Call<News>
}