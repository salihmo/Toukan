package com.learning.toukan.activities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.learning.toukan.Constants
import com.learning.toukan.R
import com.learning.toukan.models.Answer
import com.learning.toukan.models.Question
import com.learning.toukan.models.Quiz
import kotlinx.android.synthetic.main.activity_second.*
import kotlin.random.Random

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    private var currentQuestion: Int = 1
    private var questionList: MutableList<Question> = mutableListOf()
    private var theOption: Int = 0
    private var correctAnswer: Int = 0
    private var bAnswerSelected = false

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val db = FirebaseFirestore.getInstance()
        val q = intent.getParcelableExtra<Quiz>("quiz")
        if (q == null) {
            finish()
            return
        } else {
            quiz = q
        }
        Toast.makeText(this, "Quiz: ${quiz.title}", Toast.LENGTH_SHORT).show()
        val questions = db.collection("quizzes").document(quiz.id).collection("questions")
        questions.get().addOnSuccessListener { result ->
            for (row in result) {
                val question = row.toObject(Question::class.java)
                val correctPosition = Random.nextInt(0, 4)
                question.incorrect.shuffle()
                question.correctPosition = correctPosition
                questionList.add(question)
            }
            questionList.shuffle()
            placeQuestion()
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
        }

        // show quiz title with quiz.title

        if (tv_question.text.length > 100) {
            tv_question.textSize = 20f
        } else
            tv_question.textSize = 25f


        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)

        btn_submit.isEnabled = bAnswerSelected
    }

    private fun placeQuestion() {
        ll_question.visibility = View.VISIBLE
        val question = questionList!![currentQuestion - 1]
        defaultOptions()
        if (currentQuestion == questionList!!.size) {
            btn_submit.text = "Next"
        } else {
            btn_submit.text = "Submit"
        }
        progressBar.progress = currentQuestion
        progressBar.max = questionList.size
        tv_progress.text = "$currentQuestion" + "/" + progressBar.max
        tv_question.text = question!!.title
        iv_image.setImageResource(Constants.getImageForId(question.image))
        val options = question.incorrect
        options.add(question.correctPosition, question.correct)
        setupAnswer(options[0], 0, tv_option_one)
        setupAnswer(options[1],1, tv_option_two)
        setupAnswer(options[2],2, tv_option_three)
        setupAnswer(options[3],3, tv_option_four)
    }

    private fun defaultOptions() {
        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.option_border)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_submit -> {
                if (theOption == -1) {
                    currentQuestion++
                    when {
                        currentQuestion <= questionList!!.size -> {
                            placeQuestion()
                        }
                        else -> {
                            val intent = Intent(this, ResultsActivity::class.java)
                            intent.putExtra(Constants.quiz, quiz)
                            intent.putExtra(Constants.totalCorrect, correctAnswer)
                            intent.putExtra(Constants.totalQuestions, questionList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                    bAnswerSelected = false
                    btn_submit.isEnabled = bAnswerSelected
                    setEnableOptions(!bAnswerSelected)
                } else {
                    setEnableOptions(!bAnswerSelected)
                    val theQuestion = questionList?.get(currentQuestion - 1)
                    if (theQuestion!!.correctPosition != theOption) {
                        answerObserver(
                            theOption,
                            R.drawable.wronganswer_option_border
                        )
                    } else {
                        correctAnswer++
                    }
                    answerObserver(
                        theQuestion.correctPosition,
                        R.drawable.rightanswer_option_border
                    )

                    if (currentQuestion == questionList!!.size) {
                        btn_submit.text = "Finish"
                    } else {
                        btn_submit.text = "Next"
                    }
                    theOption = -1
                }
            }
        }
    }

    private fun answerObserver(answer: Int, answerView: Int) {
        when (answer) {
            0 -> {
                tv_option_one.background = ContextCompat.getDrawable(this, answerView)
            }
            1 -> {
                tv_option_two.background = ContextCompat.getDrawable(this, answerView)
            }
            2 -> {
                tv_option_three.background = ContextCompat.getDrawable(this, answerView)
            }
            3 -> {
                tv_option_four.background = ContextCompat.getDrawable(this, answerView)
            }
        }
    }

    private fun selectedOption(tv: TextView, optionNumber: Int) {
        defaultOptions()
        theOption = optionNumber
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border
        )
        bAnswerSelected = true
        btn_submit.isEnabled = bAnswerSelected
    }


    private fun setEnableOptions(bEnable: Boolean) {
        first_two_options.isClickable = bEnable
        bottom_two_options.isClickable = bEnable

        tv_option_one.isEnabled = bEnable
        tv_option_two.isEnabled = bEnable
        tv_option_three.isEnabled = bEnable
        tv_option_four.isEnabled = bEnable

        tv_option_one.isClickable = bEnable
        tv_option_two.isClickable = bEnable
        tv_option_three.isClickable = bEnable
        tv_option_four.isClickable = bEnable
    }

    fun setupAnswer(answer: Answer, optionNumber: Int, textView: TextView) {
        textView.text = answer.text
        textView.setOnClickListener {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(this, Constants.getSoundForId(answer.key))
            mediaPlayer?.start()
            selectedOption(textView, optionNumber)
        }
    }


   override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit Stage?")
        builder.setIcon(R.drawable.backarrow)
        builder.setMessage("Are you sure you want to exit the stage?\nNo progress will be saved and you will earn no tokens.")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
            super.onBackPressed()
            finish()
        })
        builder.setNegativeButton("No", DialogInterface.OnClickListener { _, _ ->  })
        builder.show()
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.in_left_slide, R.anim.out_slide_right)
    }

}




