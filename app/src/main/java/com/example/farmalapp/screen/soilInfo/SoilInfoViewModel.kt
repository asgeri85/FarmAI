package com.example.farmalapp.screen.soilInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmalapp.api.FarmRetofit
import com.example.farmalapp.screen.soildetail.ApiStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class SoilInfoViewModel : ViewModel() {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val ref = database.getReference("values")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status


    fun uploadValue(ph: String, nitrogen: String, phosphorus: String, potassium: String) {
        _status.value = ApiStatus.LOADING
        val id = auth.currentUser!!.uid
        val hashMap = HashMap<String, String>()
        hashMap["ph"] = ph
        hashMap["fosfor"] = phosphorus
        hashMap["azot"] = nitrogen
        hashMap["potasyum"] = potassium

        ref.child(id).setValue(hashMap).addOnCompleteListener {
            if (it.isSuccessful) {
                _status.value = ApiStatus.DONE
            } else {
                _status.value = ApiStatus.ERROR
            }
        }
    }


}