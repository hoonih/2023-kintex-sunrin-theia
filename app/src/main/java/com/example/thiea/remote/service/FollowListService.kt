package com.example.thiea.remote.service

import com.example.thiea.data.model.FollowerInfo
import com.example.thiea.data.model.FollowingInfo
import com.example.thiea.data.model.User
import com.example.thiea.data.model.message
import com.example.thiea.data.model.recent
import com.example.thiea.data.model.userSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FollowListService {
    @GET("/user_following/")
    fun requestFollowingList(
        @Query("user_id") user_id: String
    ): Call<FollowingInfo>

    @GET("/user_followers/")
    fun requestFollowerList(
        @Query("user_id") user_id: String
    ): Call<FollowerInfo>

    @GET("/user/{uid}/last_post")
    fun profilelastpost(
        @Path("uid") uid: String,
    ): Call<recent>
    @POST("/unfollow/")
    fun unfollow(
        @Query("follower_id") user_id: String,
        @Query("followed_id") followed_id: String,
    ): Call<message>

    @GET("/user/{uid}/search_users_by_name/")
    fun searchbyname(
        @Query("name") name: String,
    ): Call<User>
}