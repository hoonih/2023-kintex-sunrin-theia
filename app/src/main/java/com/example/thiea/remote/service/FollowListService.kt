package com.example.thiea.remote.service

import com.example.thiea.data.model.userSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowListService {
    @GET("/user_following/")
    fun requestFollowList(
        @Query("user_id") user_id: String
    ): Call<List<userSearch>>
}