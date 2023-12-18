package com.example.thiea.remote

import com.example.thiea.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val clientBuilder = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://naveropenapi.apigw.ntruss.com/")

    private val retrofitBuilder2: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.Base_URL)

    fun getRetrofit(): Retrofit {
        retrofitBuilder.client(clientBuilder.build())
        return retrofitBuilder.build()
    }

    fun getRetrofitmain(): Retrofit {
        retrofitBuilder2.client(clientBuilder.build())
        return retrofitBuilder2.build()
    }
}