package com.example.thiea.data.model

import com.google.gson.annotations.SerializedName

data class pinglocationreq (
    val latitude : Double,
    val longitude : Double,
)
class PostsResponse {
    @SerializedName("nearby_followed_posts")
    val posts: List<Post>? = null
    @SerializedName("close_friends_posts")
    val closeposts: List<Post>? = null
}