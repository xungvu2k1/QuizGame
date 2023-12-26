package com.example.quizgame.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository(var application: Application) {

    val firebaseUserMutableLiveData = MutableLiveData<FirebaseUser?>()
    val firebaseAuth = FirebaseAuth.getInstance()

    fun signUp(email: String?, password: String?): Boolean {
        if (email != null && password != null && email!!.isNotEmpty() && password!!.isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUserMutableLiveData.value = firebaseAuth.currentUser
                } else {
                    Toast.makeText(application, task.exception.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            return true
        }
        return false
    }

    fun signIn(email: String?, password: String?): Boolean {
        if (email != null && password != null && email!!.isNotEmpty() && password!!.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password).apply {
                Log.e("mycodeisblocking",email + password + "inside")
                addOnCompleteListener  { task ->
                    Log.e("mycodeisblocking","email + password")
                    if (task.isSuccessful) {
                        Log.e("mycodeisblocking","email + password")
                        firebaseUserMutableLiveData.postValue(firebaseAuth.currentUser)
                    } else {
                        Toast.makeText(application, task.exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            return true
        }
        return false
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}