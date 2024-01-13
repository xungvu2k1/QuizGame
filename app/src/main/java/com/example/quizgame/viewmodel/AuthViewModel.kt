package com.example.quizgame.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizgame.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(application: Application) : AndroidViewModel(application) { // chứa ngữ cảnh của ứng dụng
    private var repository = AuthRepository()

    private var _currentUserLiveData = MutableLiveData<FirebaseUser>()
    val currentUserLiveData: LiveData<FirebaseUser?> = _currentUserLiveData

    private var _errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val errorMessageLiveData: LiveData<String> = _errorMessageLiveData

    fun signUp(email: String, password: String) {
        repository.signUp(
            email,
            password,
            onComplete = { user ->
                _currentUserLiveData.postValue(user)
            },
            onError = { errorMessage ->
                _errorMessageLiveData.postValue(errorMessage)
            }
        )
    }

    fun signIn(email: String, password: String) {
        repository.signIn(
            email,
            password,
            onComplete = { user ->
                _currentUserLiveData.postValue(user)
            },
            onError = { errorMessage ->
                _errorMessageLiveData.postValue(errorMessage)
            }
        )
    }

    fun signOut() {
        repository.signOut(
            onComplete = { user ->
                _currentUserLiveData.postValue(user)
            },
            onError = { errorMessage ->
                _errorMessageLiveData.postValue(errorMessage)
            }
        )
    }

    fun sendEmailToResetPassword(email: String) {
        repository.sendEmailToResetPassword(email)
    }
}