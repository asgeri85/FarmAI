package com.example.farmalapp.api

import com.example.farmalapp.model.CropResponseModel
import com.example.farmalapp.model.SoilResponseModel
import retrofit2.http.GET

interface FarmApi {

    @GET("Toprak_Analizi/cikti.json")
    suspend fun getSoilDetail(): SoilResponseModel

    @GET("Mahsul_Analizi/mahsul.json")
    suspend fun getCrops(): CropResponseModel
}