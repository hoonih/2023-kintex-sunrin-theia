package com.example.thiea.data.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Multipart

data class Post (
    val author_uid : String,
    val text : String,
    val longitude: Double,
    val latitude: Double,
    val photo_url: String,
    val for_close_friends: Boolean,
    val sentiment : Int,
    )

data class Location(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)