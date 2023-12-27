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
import com.example.quizgame.model.Question
import com.example.quizgame.model.Score
import com.example.quizgame.viewmodel.QuestionViewModel


class QuestionFragment : Fragment() {
    private lateinit var viewModel : QuestionViewModel
    private lateinit var navController: NavController
    private lateinit var progressBar: ProgressBar
    private lateinit var option1Btn : Button
    private lateinit var option2Btn : Button
    private lateinit var option3Btn : Button
    private lateinit var option4Btn : Button
    private lateinit var nextQueBtn : Button
    private lateinit var finishBtn : Button
    private lateinit var questionTv : TextView
    private lateinit var questionNumberTv : TextView
    private lateinit var timerCountTv : TextView
    private lateinit var correctAnswerText : TextView
    private lateinit var wrongAnswerText : TextView
    private lateinit var questionCountText: TextView
    private lateinit var timeCountText: TextView
    private lateinit var answer : String
    private lateinit var num_correct_answerTv : TextView
    private lateinit var num_incorrect_answerTv : TextView


    private var countdownTimer : CountDownTimer? = null
    private var correctAnswerNum : Int = 0
    private var incorrectAnswerNum : Int = 0
    private var wrongTemp : Int = 0 // biến tạm thời lưu trữ câu sai trước thời điểm chọn đáp án
    private var correctTemp : Int = 0
    private var isAnswerSelected: Boolean = false
    private var isIncreased : Boolean = false // đánh dấu đã tăng câu sai

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
        finishBtn = view.findViewById(R.id.finishBtn)
        questionTv = view.findViewById(R.id.questionContent)
        correctAnswerText = view.findViewById(R.id.textView4)
        wrongAnswerText = view.findViewById(R.id.textView2)
        questionCountText = view.findViewById(R.id.textViewx)
        timeCountText = view.findViewById(R.id.timeCount_text)

        questionNumberTv = view.findViewById(R.id.questionNumber)
        timerCountTv = view.findViewById(R.id.tv_timer_count)
        num_correct_answerTv = view.findViewById(R.id.num_correct_answer)
        num_incorrect_answerTv = view.findViewById(R.id.num_incorrect_answer)

        finishBtn.setOnClickListener{
            //ấn finish thì chuyển đi số câu đúng, các câu chưa chọn thì sai hết
            val score = Score(viewModel.getQuestionNum()!! - correctAnswerNum, correctAnswerNum)
            val bundle = Bundle().apply {
                putParcelable("scoreKey", score)
            }
            //show dialog and move to result page
            showQuizGameDialog(requireContext(), bundle)
        }
        nextQueBtn.setOnClickListener{
//            if (wrongTemp == viewModel.getIncorrectAnswerNum() && correctTemp == viewModel.getCorrectAnswerNum() ){
//                incorrectAnswerNum ++
//                num_incorrect_answerTv.text = incorrectAnswerNum.toString()
//                viewModel.setIncorrectAnswerNum(incorrectAnswerNum)
//            }
            if (!isAnswerSelected && !isIncreased ) {
                // Người dùng không chọn đáp án, tăng số câu sai lên 1
                incorrectAnswerNum++
                num_incorrect_answerTv.text = incorrectAnswerNum.toString()
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
        option1Btn.setBackgroundColor(Color.WHITE)
        option2Btn.setBackgroundColor(Color.WHITE)
        option3Btn.setBackgroundColor(Color.WHITE)
        option4Btn.setBackgroundColor(Color.WHITE)
        option1Btn.isEnabled = true
        option2Btn.isEnabled = true
        option3Btn.isEnabled = true
        option4Btn.isEnabled = true
        countdownTimer?.cancel()
        timerCountTv.text = ""
    }

    private fun loadQuestions(currentIndexQuestion : Int){
        hideQuizViews() // ẩn các view để hiển thị progressbar
        viewModel.getQuestionMutableLiveData().observe(viewLifecycleOwner){
            //Observer override onChanged
            listQuestion->
            if (listQuestion != null){
                showQuizViews() // hiển thị các view
                displayQuizData(listQuestion[currentIndexQuestion], currentIndexQuestion)
                viewModel.setCurrentIndexQuestion(currentIndexQuestion)
            }
        }
    }
    private fun hideQuizViews() {
        option1Btn.visibility = View.INVISIBLE
        option3Btn.visibility = View.INVISIBLE
        option2Btn.visibility = View.INVISIBLE
        option4Btn.visibility = View.INVISIBLE
        questionTv.visibility = View.INVISIBLE
        nextQueBtn.visibility = View.INVISIBLE
        finishBtn.visibility = View.INVISIBLE
        correctAnswerText.visibility = View.INVISIBLE
        wrongAnswerText.visibility = View.INVISIBLE
        num_correct_answerTv.visibility = View.INVISIBLE
        num_incorrect_answerTv.visibility = View.INVISIBLE
        questionCountText.visibility = View.INVISIBLE
        timeCountText.visibility = View.INVISIBLE
        timerCountTv.visibility = View.INVISIBLE
        questionNumberTv.visibility = View.INVISIBLE
    }
    private fun showQuizViews() {
        progressBar.visibility = View.INVISIBLE
        option1Btn.visibility = View.VISIBLE
        option3Btn.visibility = View.VISIBLE
        option2Btn.visibility = View.VISIBLE
        option4Btn.visibility = View.VISIBLE
        questionTv.visibility = View.VISIBLE
        nextQueBtn.visibility = View.VISIBLE
        finishBtn.visibility = View.VISIBLE
        correctAnswerText.visibility = View.VISIBLE
        wrongAnswerText.visibility = View.VISIBLE
        num_correct_answerTv.visibility = View.VISIBLE
        num_incorrect_answerTv.visibility = View.VISIBLE
        questionCountText.visibility = View.VISIBLE
        timeCountText.visibility = View.VISIBLE
        timerCountTv.visibility = View.VISIBLE
        questionNumberTv.visibility = View.VISIBLE
    }

    fun displayQuizData(questionModel : Question, questionOrder: Int){
        isAnswerSelected = false
        isIncreased = false

        countdownTimer?.cancel()

        questionTv.text = questionModel.questionContent
        option1Btn.text = questionModel.option_a
        option2Btn.text = questionModel.option_b
        option3Btn.text = questionModel.option_c
        option4Btn.text = questionModel.option_d
        answer = questionModel.answer
        questionNumberTv.text = (questionOrder+1).toString()

        option1Btn.setOnClickListener{
            checkAnswer(questionModel.option_a.trim(), questionModel.answer.trim())
            disableButton()
        }
        option2Btn.setOnClickListener{
            checkAnswer(questionModel.option_b.trim(), questionModel.answer.trim())
            disableButton()
        }
        option3Btn.setOnClickListener{
            checkAnswer(questionModel.option_c.trim(), questionModel.answer.trim())
            disableButton()
        }
        option4Btn.setOnClickListener{
            checkAnswer(questionModel.option_d.trim(), questionModel.answer.trim())
            disableButton()
        }
        countTimer()

    }
    fun checkAnswer(selectedAns : String, answer : String){
        when (answer){
            option1Btn.text.toString() -> setButtonBackground(option1Btn, true)
            option2Btn.text.toString() -> setButtonBackground(option2Btn, true)
            option3Btn.text.toString() -> setButtonBackground(option3Btn, true)
            option4Btn.text.toString() -> setButtonBackground(option4Btn, true)
        }
        if (selectedAns == answer){
            // tăng số câu hỏi đúng
            Log.e("check correct", "Dung that")
            correctTemp = correctAnswerNum // lưu lại giá trị correct
            correctAnswerNum++
            num_correct_answerTv.text = correctAnswerNum.toString()
            viewModel.setCorrectAnswerNum(correctAnswerNum)
        } else {
            wrongTemp = incorrectAnswerNum
            Log.e("checkwrong", "else1: $wrongTemp")
            incorrectAnswerNum += 1
            isIncreased = true
            Log.e("checkwrong", "else2: $wrongTemp")
            num_incorrect_answerTv.text = incorrectAnswerNum.toString()
            viewModel.setIncorrectAnswerNum(incorrectAnswerNum)
            Log.e("check incorrect", "Sai that")

            when (selectedAns) {
                option1Btn.text.toString() -> setButtonBackground(option1Btn, false)
                option2Btn.text.toString() -> setButtonBackground(option2Btn, false)
                option3Btn.text.toString() -> setButtonBackground(option3Btn, false)
                option4Btn.text.toString() -> setButtonBackground(option4Btn, false)
            }
        }
        // đánh dấu user đã chọn đáp án
        isAnswerSelected = true

    }
    private fun disableButton(){
        option1Btn.isEnabled = false
        option2Btn.isEnabled = false
        option3Btn.isEnabled = false
        option4Btn.isEnabled = false
    }
    fun countTimer(){
        countdownTimer = object : CountDownTimer(3000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                // Thực hiện hành động trong mỗi tick (1 giây trong trường hợp này)
                val secondsRemaining = millisUntilFinished / 1000
                timerCountTv.text = "$secondsRemaining"
            }

            override fun onFinish() {
                // Thực hiện hành động khi đếm ngược kết thúc
                timerCountTv.text = "0"

                if (!isAnswerSelected) {
                    // Nếu không chọn đáp án
                    incorrectAnswerNum++
                    isIncreased = true
                    num_incorrect_answerTv.text = incorrectAnswerNum.toString()
                    viewModel.setIncorrectAnswerNum(incorrectAnswerNum)
                }

                questionTv.text = "Sorry, Time is up! Continue \n with next question."
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