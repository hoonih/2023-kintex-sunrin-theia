package com.example.thiea.remote.service

import com.example.thiea.data.model.User
import com.example.thiea.data.model.recent
import com.example.thiea.data.model.userSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FollowListService {
    @GET("/user_following/")
    fun requestFollowList(
        @Query("user_id") user_id: String
    ): Call<List<userSearch>>

    @GET("/user/{uid}/last_post")
    fun profilelastpost(
        @Path("uid") uid: String,
    ): Call<recent>

    @GET("/user/{uid}/search_users_by_name/")
    fun searchbyname(
        @Query("name") name: String,
    ): Call<User>
}