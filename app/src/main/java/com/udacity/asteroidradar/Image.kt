package com.udacity.asteroidradar

import com.squareup.moshi.Json

data class Image(
    @Json(name = "url")
    val url:String,
    @Json(name = "title")
    val title:String,
)
