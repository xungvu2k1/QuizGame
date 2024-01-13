package com.example.quizgame.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mylibrary.ChangeContent
import com.example.quizgame.R
import com.example.quizgame.databinding.FragmentResultBinding
import com.example.quizgame.model.Score
import com.example.quizgame.viewmodel.ScoreViewModel

class ResultFragment : Fragment() {
    private lateinit var mFragmentResultBinding : FragmentResultBinding
    private lateinit var viewModel : ScoreViewModel
    private lateinit var navController: NavController

    private lateinit var tv_check_lib_module : TextView


    private var correctAnswerNum : Int = 0
    private var incorrectAnswerNum : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ScoreViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFragmentResultBinding = FragmentResultBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mFragmentResultBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        getBundle()
        mFragmentResultBinding.idNumCorrecAnswer.text = correctAnswerNum.toString()
        mFragmentResultBinding.idNumIncorrecAnswer.text = incorrectAnswerNum.toString()

        // gửi data lên firebase
        viewModel.sendScore(Score(incorrectAnswerNum, correctAnswerNum))

        // xử lý sự kiện
        mFragmentResultBinding.btnPlayAgain.setOnClickListener{
            navController.navigate(R.id.questionFragment)
        }

        mFragmentResultBinding.btnExit.setOnClickListener{
            navController.navigate(R.id.loginFragment)
        }
        mFragmentResultBinding.tvCheckLibModule.setOnClickListener{
            mFragmentResultBinding.tvCheckLibModule.text = ChangeContent().textButtonContent
        }

    }

    fun getBundle(){
        val resultBundle = arguments
        if (resultBundle != null) {
            val result: Score? = resultBundle.getParcelable("scoreKey")

            if (result != null) {
                incorrectAnswerNum = result.incorrectNum
                correctAnswerNum = result.correctNum

                // Sử dụng incorrectNum và correctNum ở đây
                Log.d("ScoreFragment", "Incorrect Num: $incorrectAnswerNum, Correct Num: $correctAnswerNum")
            }
        }
    }
}