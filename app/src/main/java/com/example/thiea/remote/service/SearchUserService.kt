package com.example.thiea.remote.service

import com.example.thiea.data.model.User
import com.example.thiea.data.model.is_following
import com.example.thiea.data.model.userSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchUserService {
    @GET("/search_users_by_name/")
    fun search_users_by_name(
        @Query("name") name: String
    ): Call<List<userSearch>>


    @GET("/is_following_nickname/")
    fun isfollowing(
        @Query("follower_id") follower_id: String,
        @Query("nickname") nickname: String
    ): Call<is_following>

    @GET("/user_details/")
    fun usersearch(
        @Query("user_id") user_id: String,
    ): Call<userSearch>
}