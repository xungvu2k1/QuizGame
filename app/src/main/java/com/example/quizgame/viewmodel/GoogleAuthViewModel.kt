package com.example.quizgame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizgame.repository.GoogleAuthRepository

class GoogleAuthViewModel : ViewModel(){

    private val _signInSuccessLiveData : MutableLiveData<Boolean> = MutableLiveData()
    val signInSuccessLiveData : LiveData<Boolean> get() = _signInSuccessLiveData
    private var googleAuthRepository : GoogleAuthRepository = GoogleAuthRepository()

    fun signInWithGoogle(idToken : String) {
        googleAuthRepository.signInWithGoogle(idToken) { isSuccess ->
            _signInSuccessLiveData.postValue(isSuccess)
        }
    }

}