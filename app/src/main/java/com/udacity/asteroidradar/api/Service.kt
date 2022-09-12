package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.Image
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

interface nasaService {
    @GET("neo/rest/v1/feed")
     suspend fun getProperties(
        @Query("start_date")
        start_date: String,
        @Query("end_date")
        end_date: String,
        @Query("api_key")
        apiKey: String
    ) : String
    @GET("planetary/apod")
    suspend fun getImages(
        @Query("api_key") api: String
    ) : Image
}

object ABI {

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

    val server = retrofit.create(nasaService::class.java)
}