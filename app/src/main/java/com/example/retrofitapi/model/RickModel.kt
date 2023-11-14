package com.example.retrofitapi.model

import com.google.gson.annotations.SerializedName

data class RickModel(
    @SerializedName("results")
    val `result`:List<RickData>
)

data class RickData(
    @SerializedName("id")
    val `id`:Int,
    @SerializedName("name")
    val `name`:String,
    @SerializedName("species")
    val `species`:String,
    @SerializedName("gender")
    val `gender`:String,
    @SerializedName("status")
    val `status`:String,
    @SerializedName("image")
    val image: String
)

