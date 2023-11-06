package com.example.thiea.remote.service

import com.example.thiea.data.model.Post
import com.example.thiea.data.model.Postreq
import com.example.thiea.data.model.PostsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PingService {
    @POST("/post/")
    fun pingcreate(
        @Body post : Postreq
    ): Call<Post>

    @GET("/nearby_posts/")
    fun pingsearch(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Call<PostsResponse>
}