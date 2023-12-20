package com.example.thiea.remote.service

import com.example.thiea.data.model.Post
import com.example.thiea.data.model.Postreq
import com.example.thiea.data.model.PostsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

interface PingService {
    @Multipart
    @POST("/create_post")
    fun pingcreate(
        @Query("author_uid") authorUid: String,
        @Query("text") text: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("sentiment") sentiment: Int,
        @Query("for_close_friends") forCloseFriends: Boolean,
        @Part file: MultipartBody.Part? // Nullable for optional file
    ): Call<Post> // Define PostResponse based on your JSON structure


    @GET("/user/{uid}/nearby_pings/")
    fun pingsearch(
        @Path("uid") uid: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Call<PostsResponse>
}