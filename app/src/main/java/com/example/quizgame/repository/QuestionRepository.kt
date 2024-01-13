package com.example.quizgame.repository

import android.util.Log
import com.example.quizgame.model.Question
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class QuestionRepository {
    private val firebaseFirestore: FirebaseFirestore = Firebase.firestore
    private var _questionNum : Int? = null
    private lateinit var _listQuestion : MutableList<Question>
    fun getQuestionNum():Int?{
        return _questionNum
    }

    fun getQuestions(onQuestionLoad : (MutableList<Question>?) -> Unit){
        _listQuestion = mutableListOf()
        firebaseFirestore.collection("questions").get()
            .addOnSuccessListener{listQuestion->
                for (question in listQuestion){
                    val _question = question.toObject(Question::class.java)
                    _listQuestion.add(_question)
                }
                _questionNum = _listQuestion.size
                onQuestionLoad(_listQuestion) // callback
            }.addOnFailureListener{
                Log.e("mycodeisblocking", "Error getting data")
            }
    }
}