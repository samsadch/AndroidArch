package com.samsad.kotlinmarsnetwork.network

import com.squareup.moshi.Json

data class MarsProperty(
    val id: String,
    @Json(name = "img_src") val imageSourceUrl: String,
    val type: String,
    val price: Double
)
