package com.example.farmalapp.screen.tahephoto

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.farmalapp.screen.soildetail.ApiStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage


class PhotoViewModel : ViewModel() {

    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    fun uploadImage(uri: Uri) {
        _status.value = ApiStatus.LOADING
        val imageName = "${auth.currentUser!!.uid}.jpg"
        storage.reference.child(imageName).putFile(uri).addOnCompleteListener {
            if (it.isSuccessful) {
                _status.value = ApiStatus.DONE
            } else {
                _status.value = ApiStatus.ERROR
            }
        }
    }
}