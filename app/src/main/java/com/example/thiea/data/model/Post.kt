package com.example.thiea.data.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Multipart

data class Post (
    val post_id : Int,
    val author_uid : String,
    val author : User,
    val text : String,
    val title : String,
    val location: Location,
    val sentiment : Int,
    )

data class Location(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)