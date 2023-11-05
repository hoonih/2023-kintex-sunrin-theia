package com.example.thiea.remote.service

import com.example.thiea.data.model.reversegeocode
import com.example.thiea.ui.util.navermapkey.Companion.NAVER_KEY
import com.example.thiea.ui.util.navermapkey.Companion.NAVER_SECRET
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ReversegeoService {
    @GET("map-reversegeocode/v2/gc")
    @Headers("X-NCP-APIGW-API-KEY-ID:${NAVER_KEY}",
        "X-NCP-APIGW-API-KEY:${NAVER_SECRET}")
    fun getReversgeo(
        @Query("coords") coords: String,
        @Query("output") output: String = "json",
        @Query("orders") orders: String = "admcode"
    ): Call<reversegeocode>
}