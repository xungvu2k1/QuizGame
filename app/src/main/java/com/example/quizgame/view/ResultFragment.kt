package com.example.quizgame.view

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
import com.example.quizgame.R
import com.example.quizgame.model.Score
import com.example.quizgame.viewmodel.ScoreViewModel

class ResultFragment : Fragment() {
    private lateinit var viewModel : ScoreViewModel
    private lateinit var navController: NavController
    private lateinit var exitBtn : Button
    private lateinit var playAgainBtn : Button
    private lateinit var num_correct_answerTv : TextView
    private lateinit var num_incorrect_answerTv : TextView


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        exitBtn = view.findViewById(R.id.btn_exit)
        playAgainBtn = view.findViewById(R.id.btn_play_again)
        num_correct_answerTv = view.findViewById(R.id.id_num_correc_answer)
        num_incorrect_answerTv = view.findViewById(R.id.id_num_incorrec_answer)


        getBundle()
        num_correct_answerTv.text = correctAnswerNum.toString()
        num_incorrect_answerTv.text = incorrectAnswerNum.toString()

        // gửi data lên firebase
        viewModel.sendScore(Score(incorrectAnswerNum, correctAnswerNum))

        // xử lý sự kiện
        playAgainBtn.setOnClickListener{
            navController.navigate(R.id.questionFragment)
        }

        exitBtn.setOnClickListener{
            navController.navigate(R.id.loginFragment)
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