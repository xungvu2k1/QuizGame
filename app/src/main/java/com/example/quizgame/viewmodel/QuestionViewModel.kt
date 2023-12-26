package com.example.quizgame.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizgame.model.Question
import com.example.quizgame.repository.QuestionRepository

class QuestionViewModel : ViewModel(), QuestionRepository.OnQuestionLoad {
    private lateinit var questionMutableLiveData: MutableLiveData<List<Question>> // nhận vào 1 list các question
    private lateinit var repository : QuestionRepository

    init {
        questionMutableLiveData = MutableLiveData()
        repository = QuestionRepository(this)
    }

    fun getQuestionMutableLiveData() : MutableLiveData<List<Question>>{
        return questionMutableLiveData
    }

    fun getQuestions(){
        repository.getQuestions()
    }

    fun setQuestionId (questionID : String){
        repository.setQuestionID(questionID)
    }

    override fun onLoad(questionModels: MutableList<Question>) {
        questionMutableLiveData.value = questionModels
//        questionMutableLiveData.value = repository.getQuestions()
    }

    override fun onError(e: Exception?) {
        Log.d("QuestionsError", "onError: " + e?.message)
    }
}