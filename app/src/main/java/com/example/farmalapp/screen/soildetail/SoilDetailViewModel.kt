package com.example.farmalapp.screen.soildetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmalapp.api.FarmRetofit
import com.example.farmalapp.model.CropResponseModel
import com.example.farmalapp.model.SoilResponseModel
import kotlinx.coroutines.launch

enum class ApiStatus { DONE, LOADING, ERROR }

class SoilDetailViewModel : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _soilDetail = MutableLiveData<SoilResponseModel>()
    val soilDetail: LiveData<SoilResponseModel>
        get() = _soilDetail

    private val _crops = MutableLiveData<CropResponseModel>()
    val crops: LiveData<CropResponseModel>
        get() = _crops


    fun getSoilData() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING

            try {
                _soilDetail.value = FarmRetofit.retrofitService.getSoilDetail()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun getCrops() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _crops.value = FarmRetofit.retrofitService.getCrops()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }
}