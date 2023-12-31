package com.example.thiea.data.model

import com.google.gson.annotations.SerializedName

data class userSearch(
    @SerializedName("name")
    val name : String,
    @SerializedName("uid")
    val uid : String,
    @SerializedName("phone")
    val phone : String,
    @SerializedName("gender")
    val gender : String,
    @SerializedName("profile_picture_url")
    val profile_picture_url : String
)

data class FollowingInfo(
    @SerializedName("following")
    val following : List<userSearch>
)
data class FollowerInfo(
    @SerializedName("followers")
    val following : List<userSearch>
)
data class is_following(
    val is_following : Boolean
)

data class message(
    val message: String
)