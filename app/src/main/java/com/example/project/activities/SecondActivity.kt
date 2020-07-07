package com.example.project.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.project.Constants
import com.example.project.Question
import com.example.project.R
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity(), View.OnClickListener {

    private var currentQuestion: Int = 1
    private var questionList: ArrayList<Question>? = null
    private var theOption: Int = 0
    private var correctAnswer: Int = 0
    private var bAnserSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        questionList = Constants.getQuestions()

        placeQuestion()
        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)

        btn_submit.isEnabled = bAnserSelected
    }
    private fun placeQuestion() {
        val question = questionList!![currentQuestion - 1]
        defaultOptions()
        if(currentQuestion == questionList!!.size) {
            btn_submit.text = "Next"
        }else{
            btn_submit.text = "Submit"
        }
        progressBar.progress = currentQuestion
        tv_progress.text = "$currentQuestion" + "/" + progressBar.max
        tv_question.text = question!!.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
    }
    private fun defaultOptions(){
        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,
                R.drawable.option_border
            )
        }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_option_one -> {selectedOption(tv_option_one, 1)
            }
            R.id.tv_option_two -> {selectedOption(tv_option_two, 2)
            }
            R.id.tv_option_three -> {selectedOption(tv_option_three, 3)
            }
            R.id.tv_option_four -> {selectedOption(tv_option_four, 4)
            }
            R.id.btn_submit ->{
                if(theOption == 0) {
                    currentQuestion++
                    when {
                        currentQuestion <= questionList!!.size -> {
                            placeQuestion()
                        }
                        else -> {
                            val intent = Intent(this, Results::class.java)
                            intent.putExtra(Constants.totalCorrect, correctAnswer)
                            intent.putExtra(Constants.totalQuestions, questionList!!.size)
                            startActivity(intent)
                        }
                    }
                    bAnserSelected = false
                    btn_submit.isEnabled = bAnserSelected
                    setEnableOptions(!bAnserSelected)
                }else {
                    setEnableOptions(!bAnserSelected)
                    val theQuestion = questionList?.get(currentQuestion - 1)
                    if (theQuestion!!.rightAnswer != theOption) {
                        answerObserver(theOption,
                            R.drawable.wronganswer_option_border
                        )
                    }else {
                        correctAnswer++
                    }
                    answerObserver(theQuestion.rightAnswer,
                        R.drawable.rightanswer_option_border
                    )

                    if(currentQuestion == questionList!!.size){
                        btn_submit.text = "Finish"
                    }else{
                        btn_submit.text ="Next"
                    }
                    theOption = 0
                }
            }
        }
    }
    private fun answerObserver(answer: Int, answerView: Int){
        when(answer){
            1-> {
                tv_option_one.background = ContextCompat.getDrawable(this, answerView)
            }
            2-> {
                tv_option_two.background = ContextCompat.getDrawable(this, answerView)
            }
            3-> {
                tv_option_three.background = ContextCompat.getDrawable(this, answerView)
            }
            4-> {
                tv_option_four.background = ContextCompat.getDrawable(this, answerView)
            }
        }
    }
    private fun selectedOption(tv: TextView, optionNumber: Int){
        defaultOptions()
        theOption = optionNumber
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,
            R.drawable.selected_option_border
        )
        bAnserSelected = true
        btn_submit.isEnabled = bAnserSelected
    }
    private fun setEnableOptions(bEnable:Boolean){
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
}