package com.rach.quizappintership

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rach.quizappintership.databinding.ActivityQuizBinding
import com.rach.quizappintership.databinding.ScoreDialogBinding

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizBinding

    companion object {
        var questionModelList: List<QuestionModel> = listOf()
        var time: String = ""
    }

    var currentQuestionIndex = 0
    var selectedAnswer = ""
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            button0.setOnClickListener(this@QuizActivity)
            button1.setOnClickListener(this@QuizActivity)
            button2.setOnClickListener(this@QuizActivity)
            button3.setOnClickListener(this@QuizActivity)

            NextButton.setOnClickListener(this@QuizActivity)
            backButton.setOnClickListener(this@QuizActivity)

        }

        loadQuestion()
        startTimer()

    }

    private fun startTimer() {

        val totaltimesinMillsec = time.toInt() * 60 * 1000L

        object : CountDownTimer(totaltimesinMillsec, 1000L) {
            override fun onTick(millsecondsUntilFinished: Long) {

                val seconds = millsecondsUntilFinished / 1000
                val minutes = seconds / 60
                val remainigSeconds = minutes % 60

                binding.timerIndicatorTextView.text =
                    String.format("%02d:%02d", minutes, remainigSeconds)


            }

            override fun onFinish() {


            }

        }.start()

    }

    private fun loadQuestion() {

        selectedAnswer = ""
        if (currentQuestionIndex == questionModelList.size) {
            finishQuiz()
            return
        }

        binding.apply {
            questionIndiactorTextView.text =
                "Question ${currentQuestionIndex + 1} / ${questionModelList.size} "
            questionProgressIndiactor.progress =
                ((currentQuestionIndex+1).toFloat() / questionModelList.size.toFloat() * 100).toInt()

            questionTextView.text = questionModelList[currentQuestionIndex].question

            button0.text = questionModelList[currentQuestionIndex].options[0]
            button1.text = questionModelList[currentQuestionIndex].options[1]
            button2.text = questionModelList[currentQuestionIndex].options[2]
            button3.text = questionModelList[currentQuestionIndex].options[3]


        }


    }


    override fun onClick(view: View?) {

        binding.apply {

            button0.setBackgroundColor(getColor(R.color.gray))
            button1.setBackgroundColor(getColor(R.color.gray))
            button2.setBackgroundColor(getColor(R.color.gray))
            button3.setBackgroundColor(getColor(R.color.gray))

        }


        val clickedButton = view as Button

        if (clickedButton.id == R.id.NextButton) {

            if (selectedAnswer == questionModelList[currentQuestionIndex].correct){
                score++
            }

            currentQuestionIndex++
            loadQuestion()

        } else if (clickedButton.id == R.id.backButton) {



            currentQuestionIndex--
            loadQuestion()


        } else {

//            clickedButton.setBackgroundColor(getColor(R.color.orange))
            selectedAnswer = clickedButton.text.toString()

            if (selectedAnswer == questionModelList[currentQuestionIndex].correct) {
                clickedButton.setBackgroundColor(getColor(R.color.green))
            } else {

                clickedButton.setBackgroundColor(getColor(R.color.red))

            }

        }

    }

    private fun finishQuiz() {

        val totalQuestions = questionModelList.size
        val percentage = (score.toFloat() / totalQuestions.toFloat() * 100).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)

        dialogBinding.apply {

            scoreProgressIndicator.progress = percentage
            scoreProgressTextPercent.text = "$percentage %"

            if (percentage > 60) {
                scoreTitle.text = "Congrats! You have Passed"
                scoreTitle.setTextColor(Color.BLUE)

            } else {
                scoreTitle.text = "Oops! You have Failed"
                scoreTitle.setTextColor(Color.RED)

            }

            scoreSubtitle.text = "$score out of $totalQuestions questions are correct"

            finishQuiz.setOnClickListener {
                finish()
            }

        }



        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()


    }
}