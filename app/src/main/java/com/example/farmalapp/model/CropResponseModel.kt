package com.example.farmalapp.model

import com.squareup.moshi.Json

data class CropResponseModel(
    @Json(name = "Mahsul")
    val crops: List<String>,
)