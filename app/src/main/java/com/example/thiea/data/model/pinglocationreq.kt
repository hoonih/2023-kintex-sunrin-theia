package com.example.thiea.data.model

import com.google.gson.annotations.SerializedName

data class pinglocationreq (
    val latitude : Double,
    val longitude : Double,
)
class PostsResponse {
    @SerializedName("posts")
    val posts: List<Post>? = null
}