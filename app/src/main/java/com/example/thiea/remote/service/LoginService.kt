package com.example.thiea.remote.service

import com.example.thiea.data.model.User
import com.example.thiea.data.model.reversegeocode
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LoginService {
    @GET("/user")
    fun userregister(
        @Query("user_id") user_id: String
    ): Call<User>

    @POST("/user/")
    fun register(
        @Body user : User
    ): Call<User>
}