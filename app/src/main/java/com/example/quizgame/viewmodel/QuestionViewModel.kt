package com.example.quizgame.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizgame.model.Question
import com.example.quizgame.repository.OnQuestionLoad
import com.example.quizgame.repository.QuestionRepository

class QuestionViewModel : ViewModel(), OnQuestionLoad {
    private var questionMutableLiveData: MutableLiveData<List<Question>> = MutableLiveData()
    private var repository : QuestionRepository = QuestionRepository(this)
    private var currentIndexQuestion : Int = 0
    private var correctAnswerNum : Int = 0
    private var incorrectAnswerNum : Int = 0

    fun setCorrectAnswerNum(correctAnswerNum: Int){
        this.correctAnswerNum = correctAnswerNum
    }

    fun setIncorrectAnswerNum(incorrectAnswerNum : Int){
        this.incorrectAnswerNum = incorrectAnswerNum
    }

    fun getQuestionNum():Int?{
        return repository.getQuestionNum()
    }
    fun getCurrentIndexQuestion() : Int{
        return currentIndexQuestion
    }
    fun setCurrentIndexQuestion(index : Int){
        currentIndexQuestion = index
    }

    fun getQuestionMutableLiveData() : MutableLiveData<List<Question>>{
        return questionMutableLiveData
    }

    fun getQuestions(){
        repository.getQuestions()
    }

    override fun onLoad(questionModels: MutableList<Question>) {
        questionMutableLiveData.value = questionModels
    }
}