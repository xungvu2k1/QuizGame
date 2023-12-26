package com.example.quizgame.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.quizgame.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(application: Application) : AndroidViewModel(application) { // chứa ngữ cảnh của ứng dụng
    private var repository = AuthRepository(application)

    var firebaseUserMutableLiveData = repository.firebaseUserMutableLiveData
    var currentUser: FirebaseUser? = repository.firebaseAuth.currentUser

    fun signUp(email: String?, passwrod: String?) {
        repository.signUp(email, passwrod)
    }

    fun signIn(email: String?, passwrod: String?) {
        repository.signIn(email, passwrod)
    }

    fun signOut() {
        repository.signOut()
    }
}