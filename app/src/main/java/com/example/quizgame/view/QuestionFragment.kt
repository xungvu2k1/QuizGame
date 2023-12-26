package com.example.quizgame.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizgame.R
import com.example.quizgame.model.Question
import com.example.quizgame.viewmodel.QuestionViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject

class QuestionFragment : Fragment() {

//    private lateinit var firestore: FirebaseFirestore
    private lateinit var viewModel : QuestionViewModel
    private lateinit var navController: NavController
    private lateinit var progressBar: ProgressBar
    private lateinit var option1Btn : Button
    private lateinit var option2Btn : Button
    private lateinit var option3Btn : Button
    private lateinit var option4Btn : Button
    private lateinit var nextQueBtn : Button
    private lateinit var questionTv : TextView
    private lateinit var questionNumberTv : TextView
    private lateinit var timerCountTv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[QuestionViewModel::class.java]
        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        progressBar = view.findViewById(R.id.pb_progress_bar)
        option1Btn = view.findViewById(R.id.option1Btnx)
        option2Btn = view.findViewById(R.id.option2Btnx)
        option3Btn = view.findViewById(R.id.option3Btnx)
        option4Btn = view.findViewById(R.id.option4Btnx)
        nextQueBtn = view.findViewById(R.id.nextQueBtnx)
        questionTv = view.findViewById(R.id.questionContent)
        questionNumberTv = view.findViewById(R.id.questionNumber)
        timerCountTv = view.findViewById(R.id.tv_timer_count)

//        questionId = QuestionFragment().arguments?.getString("questionID").toString()
//
        loadData()
        viewModel.getQuestions()


    }
    private fun loadData() {
        enableOptions()
        loadQuestions(1)
    }

    private fun enableOptions(){
        option1Btn.visibility = View.VISIBLE
        option2Btn.visibility = View.VISIBLE
        option3Btn.visibility = View.VISIBLE

        option1Btn.isEnabled = true
        option2Btn.isEnabled = true
        option3Btn.isEnabled = true

        nextQueBtn.visibility = View.INVISIBLE

    }
    private fun loadQuestions(i : Int){
        viewModel.getQuestionMutableLiveData().observe(viewLifecycleOwner){
            //Observer override onChanged
            listQuestion->
            questionTv.text = listQuestion[i-1].questionContent
            option1Btn.text = listQuestion[i-1].option_a
            option2Btn.text = listQuestion[i-1].option_b
            option3Btn.text = listQuestion[i-1].option_c
            option4Btn.text = listQuestion[i-1].option_d
        }
    }



}