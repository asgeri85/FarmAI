package com.example.farmalapp.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SoilResponseModel(
    @Json(name = "ID")
    val id: String,
    val type: String,
    @Json(name = "ozellık")
    val property: String,
    @Json(name = "Mahsul")
    val crop: List<String>,
) : Parcelable
