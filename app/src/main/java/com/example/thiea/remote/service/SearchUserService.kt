package com.example.thiea.remote.service

import com.example.thiea.data.model.User
import com.example.thiea.data.model.userSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchUserService {
    @GET("/search_users_by_name/")
    fun search_users_by_name(
        @Query("name") name: String
    ): Call<List<userSearch>>
}