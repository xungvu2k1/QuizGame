package com.example.quizgame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizgame.model.Question
import com.example.quizgame.repository.QuestionRepository

class QuestionViewModel : ViewModel() {
    private var _questionMutableLiveData: MutableLiveData<List<Question>> = MutableLiveData()
    val questionMutableLiveData : LiveData<List<Question>> get() = _questionMutableLiveData

    private var repository : QuestionRepository = QuestionRepository()
    private var currentIndexQuestion : Int = 0
    private var correctAnswerNum : Int = 0
    private var _incorrectAnswerNum : Int = 0
    val incorrectAnswerNum get() = _incorrectAnswerNum

    fun setCorrectAnswerNum(correctAnswerNum: Int){
        this.correctAnswerNum = correctAnswerNum
    }

    fun setIncorrectAnswerNum(_incorrectAnswerNum : Int){
        this._incorrectAnswerNum = _incorrectAnswerNum
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

    fun getQuestions(){
        repository.getQuestions(onQuestionLoad = { listQuestions ->
            _questionMutableLiveData.postValue(listQuestions)
        })
    }
}