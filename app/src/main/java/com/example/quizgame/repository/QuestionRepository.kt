package com.example.quizgame.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.quizgame.model.Question
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class QuestionRepository(var onQuestionLoad : OnQuestionLoad) {
    private var firebaseFirestore: FirebaseFirestore = Firebase.firestore
//    private lateinit var questionMutableLiveData : MutableLiveData<List<Question>>
    private lateinit var questionId : String

    fun setQuestionID(questionId : String){
        this.questionId = questionId
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

                onQuestionLoad.onLoad(_listQuestion)
            } else {
                Log.e("abcd", "cssssss")
            }
        }
    }

    interface OnQuestionLoad{
        fun onLoad(questionModels :  MutableList<Question>)
        fun onError(e : Exception?)
    }

}