package com.example.farmalapp.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoModel(val bitmap: Bitmap) : Parcelable

