package com.example.quizgame.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
class GoogleAuthRepository {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    fun signInWithGoogle(idToken : String , onComplete : (Boolean) -> Unit) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken , null)////lấy account trong gg aut dựa trên idToken
        auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }
}