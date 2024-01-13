package com.example.quizgame.view

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizgame.R
import com.example.quizgame.databinding.FragmentQuestionBinding
import com.example.quizgame.model.Question
import com.example.quizgame.model.Score
import com.example.quizgame.viewmodel.QuestionViewModel


class QuestionFragment : Fragment() {

    private lateinit var mFragmentQuestionBinding : FragmentQuestionBinding
    private lateinit var viewModel : QuestionViewModel
    private lateinit var navController: NavController
    private lateinit var answer : String

    private var countdownTimer : CountDownTimer? = null
    private var correctAnswerNum : Int = 0
    private var incorrectAnswerNum : Int = 0
    private var isAnswerSelected: Boolean = false
    private var isIncreased : Boolean = false // đánh dấu đã tăng câu sai

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[QuestionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mFragmentQuestionBinding = FragmentQuestionBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mFragmentQuestionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        mFragmentQuestionBinding.finishBtn.setOnClickListener{
            //ấn finish thì chuyển đi số câu đúng, các câu chưa chọn thì sai hết
            val score = Score(viewModel.getQuestionNum()!! - correctAnswerNum, correctAnswerNum)
            val bundle = Bundle().apply {
                putParcelable("scoreKey", score)
            }
            //show dialog and move to result page
            showQuizGameDialog(requireContext(), bundle)
        }
        mFragmentQuestionBinding.nextQueBtnx.setOnClickListener{
            if (!isAnswerSelected && !isIncreased ) {
                // Người dùng không chọn đáp án, tăng số câu sai lên 1
                incorrectAnswerNum++
                mFragmentQuestionBinding.numIncorrectAnswer.text = incorrectAnswerNum.toString()
                viewModel.setIncorrectAnswerNum(incorrectAnswerNum)
            }

            val _currentIndexQues = viewModel.getCurrentIndexQuestion()
            if (_currentIndexQues < viewModel.getQuestionNum()!!-1){
                viewModel.setCurrentIndexQuestion(_currentIndexQues+1)
                resetUI()
                loadQuestions(viewModel.getCurrentIndexQuestion())
            } else{
                //truyen du lieu qua bundle
                val bundle = bundleToResult(incorrectAnswerNum, correctAnswerNum)
                showQuizGameDialog(requireContext(), bundle)
            }
        }
        //load question data
        loadQuestions(viewModel.getCurrentIndexQuestion())
        viewModel.getQuestions()

    }

    fun bundleToResult(incorrectAnswerNum: Int, correctAnswerNum: Int): Bundle{
        val score = Score(incorrectAnswerNum, correctAnswerNum)
        val bundle = Bundle().apply {
            putParcelable("scoreKey", score)
        }
        return bundle
    }


    fun showQuizGameDialog(context: Context, bundle: Bundle) {

        val alertDialogBuilder = AlertDialog.Builder(context) // xay dung dialog
        alertDialogBuilder.setTitle("Quiz Game")
        alertDialogBuilder.setMessage("Congratulations!!! \n You have answered all the questions. Do you want to see the result?")

        // "Play again" button
        alertDialogBuilder.setPositiveButton("Play again") { _, _ ->
            // reset
            incorrectAnswerNum = 0
            correctAnswerNum = 0
            viewModel.setIncorrectAnswerNum(incorrectAnswerNum)
            viewModel.setIncorrectAnswerNum(correctAnswerNum)
            navController.navigate(R.id.questionFragment)
        }
        // "See result"
        alertDialogBuilder.setNegativeButton("See result") { _, _ ->
            navController.navigate(R.id.action_questionFragment_to_resultFragment, bundle)
        }

        // khởi tạo và hiển thị hộp thoại
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun resetUI() {
        mFragmentQuestionBinding.option1Btnx.setBackgroundColor(Color.WHITE)
        mFragmentQuestionBinding.option2Btnx.setBackgroundColor(Color.WHITE)
        mFragmentQuestionBinding.option3Btnx.setBackgroundColor(Color.WHITE)
        mFragmentQuestionBinding.option4Btnx.setBackgroundColor(Color.WHITE)
        mFragmentQuestionBinding.option1Btnx.isEnabled = true
        mFragmentQuestionBinding.option2Btnx.isEnabled = true
        mFragmentQuestionBinding.option3Btnx.isEnabled = true
        mFragmentQuestionBinding.option4Btnx.isEnabled = true
        countdownTimer?.cancel()
        mFragmentQuestionBinding.tvTimerCount.text = ""
    }

    private fun loadQuestions(currentIndexQuestion : Int){
        hideQuizViews()
        viewModel.questionMutableLiveData.observe(viewLifecycleOwner){
            listQuestion->
            if (listQuestion != null){
                showQuizViews()
                displayQuizData(listQuestion[currentIndexQuestion], currentIndexQuestion)
                viewModel.setCurrentIndexQuestion(currentIndexQuestion)
            }
        }
    }
    private fun hideQuizViews() {
        mFragmentQuestionBinding.option1Btnx.visibility = View.INVISIBLE
        mFragmentQuestionBinding.option3Btnx.visibility = View.INVISIBLE
        mFragmentQuestionBinding.option2Btnx.visibility = View.INVISIBLE
        mFragmentQuestionBinding.option4Btnx.visibility = View.INVISIBLE
        mFragmentQuestionBinding.questionContent.visibility = View.INVISIBLE
        mFragmentQuestionBinding.nextQueBtnx.visibility = View.INVISIBLE
        mFragmentQuestionBinding.finishBtn.visibility = View.INVISIBLE
        mFragmentQuestionBinding.textView4.visibility = View.INVISIBLE
        mFragmentQuestionBinding.textView2.visibility = View.INVISIBLE
        mFragmentQuestionBinding.numCorrectAnswer.visibility = View.INVISIBLE
        mFragmentQuestionBinding.numIncorrectAnswer.visibility = View.INVISIBLE
        mFragmentQuestionBinding.textViewx.visibility = View.INVISIBLE
        mFragmentQuestionBinding.timeCountText.visibility = View.INVISIBLE
        mFragmentQuestionBinding.tvTimerCount.visibility = View.INVISIBLE
        mFragmentQuestionBinding.questionNumber.visibility = View.INVISIBLE
    }
    private fun showQuizViews() {
        mFragmentQuestionBinding.pbProgressBar.visibility = View.INVISIBLE
        mFragmentQuestionBinding.option1Btnx.visibility = View.VISIBLE
        mFragmentQuestionBinding.option3Btnx.visibility = View.VISIBLE
        mFragmentQuestionBinding.option2Btnx.visibility = View.VISIBLE
        mFragmentQuestionBinding.option4Btnx.visibility = View.VISIBLE
        mFragmentQuestionBinding.questionContent.visibility = View.VISIBLE
        mFragmentQuestionBinding.nextQueBtnx.visibility = View.VISIBLE
        mFragmentQuestionBinding.finishBtn.visibility = View.VISIBLE
        mFragmentQuestionBinding.textView4.visibility = View.VISIBLE
        mFragmentQuestionBinding.textView2.visibility = View.VISIBLE
        mFragmentQuestionBinding.numCorrectAnswer.visibility = View.VISIBLE
        mFragmentQuestionBinding.numIncorrectAnswer.visibility = View.VISIBLE
        mFragmentQuestionBinding.textViewx.visibility = View.VISIBLE
        mFragmentQuestionBinding.timeCountText.visibility = View.VISIBLE
        mFragmentQuestionBinding.tvTimerCount.visibility = View.VISIBLE
        mFragmentQuestionBinding.questionNumber.visibility = View.VISIBLE
    }

    fun displayQuizData(questionModel : Question, questionOrder: Int){
        isAnswerSelected = false
        isIncreased = false

        countdownTimer?.cancel()

        mFragmentQuestionBinding.questionContent.text = questionModel.questionContent
        mFragmentQuestionBinding.option1Btnx.text = questionModel.option_a
        mFragmentQuestionBinding.option2Btnx.text = questionModel.option_b
        mFragmentQuestionBinding.option3Btnx.text = questionModel.option_c
        mFragmentQuestionBinding.option4Btnx.text = questionModel.option_d
        answer = questionModel.answer
        mFragmentQuestionBinding.questionNumber.text = (questionOrder+1).toString()

        mFragmentQuestionBinding.option1Btnx.setOnClickListener{
            checkAnswer(questionModel.option_a.trim(), questionModel.answer.trim())
            disableButton()
        }
        mFragmentQuestionBinding.option2Btnx.setOnClickListener{
            checkAnswer(questionModel.option_b.trim(), questionModel.answer.trim())
            disableButton()
        }
        mFragmentQuestionBinding.option3Btnx.setOnClickListener{
            checkAnswer(questionModel.option_c.trim(), questionModel.answer.trim())
            disableButton()
        }
        mFragmentQuestionBinding.option4Btnx.setOnClickListener{
            checkAnswer(questionModel.option_d.trim(), questionModel.answer.trim())
            disableButton()
        }
        countTimer()

    }

    fun checkAnswer(selectedAns : String, answer : String){
        when (answer){
            mFragmentQuestionBinding.option1Btnx.text.toString() -> setButtonBackground(mFragmentQuestionBinding.option1Btnx, true)
            mFragmentQuestionBinding.option2Btnx.text.toString() -> setButtonBackground(mFragmentQuestionBinding.option2Btnx, true)
            mFragmentQuestionBinding.option3Btnx.text.toString() -> setButtonBackground(mFragmentQuestionBinding.option3Btnx, true)
            mFragmentQuestionBinding.option4Btnx.text.toString() -> setButtonBackground(mFragmentQuestionBinding.option4Btnx, true)
        }
        if (selectedAns == answer){
            correctAnswerNum++
            mFragmentQuestionBinding.numCorrectAnswer.text = correctAnswerNum.toString()
            viewModel.setCorrectAnswerNum(correctAnswerNum)
        } else {
            incorrectAnswerNum += 1
            isIncreased = true
            mFragmentQuestionBinding.numIncorrectAnswer.text = incorrectAnswerNum.toString()
            viewModel.setIncorrectAnswerNum(incorrectAnswerNum)

            when (selectedAns) {
                mFragmentQuestionBinding.option1Btnx.text.toString() -> setButtonBackground(mFragmentQuestionBinding.option1Btnx, false)
                mFragmentQuestionBinding.option2Btnx.text.toString() -> setButtonBackground(mFragmentQuestionBinding.option2Btnx, false)
                mFragmentQuestionBinding.option3Btnx.text.toString() -> setButtonBackground(mFragmentQuestionBinding.option3Btnx, false)
                mFragmentQuestionBinding.option4Btnx.text.toString() -> setButtonBackground(mFragmentQuestionBinding.option4Btnx, false)
            }
        }
        // đánh dấu user đã chọn đáp án
        isAnswerSelected = true

    }
    private fun disableButton(){
        mFragmentQuestionBinding.option1Btnx.isEnabled = false
        mFragmentQuestionBinding.option2Btnx.isEnabled = false
        mFragmentQuestionBinding.option3Btnx.isEnabled = false
        mFragmentQuestionBinding.option4Btnx.isEnabled = false
    }
    fun countTimer(){
        countdownTimer = object : CountDownTimer(3000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                // Thực hiện hành động trong mỗi tick (1 giây trong trường hợp này)
                val secondsRemaining = millisUntilFinished / 1000
                mFragmentQuestionBinding.tvTimerCount.text = "$secondsRemaining"
            }

            override fun onFinish() {
                mFragmentQuestionBinding.tvTimerCount.text = "0"

                if (!isAnswerSelected) {
                    // Nếu không chọn đáp án
                    incorrectAnswerNum++
                    isIncreased = true
                    mFragmentQuestionBinding.numIncorrectAnswer.text = incorrectAnswerNum.toString()
                    viewModel.setIncorrectAnswerNum(incorrectAnswerNum)
                }

                mFragmentQuestionBinding.questionContent.text = "Sorry, Time is up! Continue \n with next question."
                disableButton()
            }
        }.start()
    }
    private fun setButtonBackground(button : Button , isCorrect : Boolean) {
        if (isCorrect) {
            button.setBackgroundColor(Color.GREEN)
        }
        else {
            button.setBackgroundColor(Color.RED)
        }
    }
}