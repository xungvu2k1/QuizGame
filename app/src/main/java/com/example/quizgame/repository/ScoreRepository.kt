package com.example.quizgame.repository

import android.util.Log
import com.example.quizgame.model.Score
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ScoreRepository {
    private val db : FirebaseFirestore = Firebase.firestore

    // send data to firebase
    fun sendScore(score : Score){
        val _score = hashMapOf(
            "correct" to score.correctNum,
            "wrong" to score.incorrectNum
        )
        db.collection("scores").document("Kết quả")
            .set(_score)
            .addOnSuccessListener { Log.d("sendScore", "successfully!") }
            .addOnFailureListener { e -> Log.w("sendScore", "Error", e) }
    }
}