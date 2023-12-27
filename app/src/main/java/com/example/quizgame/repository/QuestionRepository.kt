package com.example.quizgame.repository

import android.util.Log
import com.example.quizgame.model.Question
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class QuestionRepository(var onQuestionLoad : OnQuestionLoad) {
    private var firebaseFirestore: FirebaseFirestore = Firebase.firestore
    private lateinit var questionId : String
    private var questionNum : Int? = null

    fun getQuestionNum():Int?{
        return questionNum
    }

    fun getQuestions(){
        val _listQuestion = mutableListOf<Question>()
        firebaseFirestore.collection("questions").get()
            .addOnSuccessListener{listQuestion->
            if (listQuestion != null){
                for (question in listQuestion){
                    val _question = question.toObject(Question::class.java)
                    _listQuestion.add(_question)
                }
                questionNum = _listQuestion.size
                onQuestionLoad.onLoad(_listQuestion)
            } else {
                Log.e("getQuestions", "listQuestion null")
            }
        }
    }
}
interface OnQuestionLoad{
    fun onLoad(questionModels :  MutableList<Question>)
}