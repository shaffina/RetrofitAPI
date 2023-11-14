package com.example.retrofitapi.network

import com.example.retrofitapi.model.RickModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("character")
    fun getRick(): Call<RickModel>
}