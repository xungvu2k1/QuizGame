package com.example.quizgame.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository {
    val firebaseAuth = FirebaseAuth.getInstance()

    fun signUp(
        email: String,
        password: String,
        onComplete: (FirebaseUser?) -> Unit,
        onError: (String) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    onComplete(user)
                } else {
                    onError("Authentication failed")
                }
            }
    }

    fun signIn(
        email: String,
        password: String,
        onComplete: (FirebaseUser?) -> Unit,
        onError: (String) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    onComplete(user)
                } else {
                    onError("Authentication failed")
                }
            }
    }

    fun signOut(
        onComplete: (FirebaseUser?) -> Unit,
        onError: (String) -> Unit
    ) {
        firebaseAuth.signOut()
        val user = firebaseAuth.currentUser
        onComplete(user)
    }

    fun sendEmailToResetPassword(email: String) {
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("SendEmailResetPassword", "Email sent.")
                    }
                }
        }
    }