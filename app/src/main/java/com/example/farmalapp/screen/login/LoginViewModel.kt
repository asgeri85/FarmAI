package com.example.farmalapp.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.farmalapp.screen.soildetail.ApiStatus
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _isLogin = MutableLiveData<String>()
    val isLogin: LiveData<String>
        get() = _isLogin

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status


    fun login(email: String, password: String) {
        _status.value = ApiStatus.LOADING
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _status.value = ApiStatus.DONE
            } else {
                _status.value = ApiStatus.ERROR
                it.exception?.let { e ->
                    _isLogin.value = e.localizedMessage
                }
            }
        }
    }

    fun register(email: String, password: String) {
        _status.value = ApiStatus.LOADING
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _status.value = ApiStatus.DONE
                _isLogin.value = "Kayıt olma işlemi başarılı"
            } else {
                _status.value = ApiStatus.ERROR
                it.exception?.let { e ->
                    _isLogin.value = e.localizedMessage
                }
            }
        }
    }
}