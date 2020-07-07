package com.example.project.activities
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project.Constants
import com.example.project.R
import kotlinx.android.synthetic.main.results.*

class Results : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.results)

        val totalQuestions = intent.getIntExtra(Constants.totalQuestions, 0)
        val correctAnswers = intent.getIntExtra(Constants.totalCorrect, 0)

        score_is.text = "Your score is $correctAnswers out of $totalQuestions"
        val coinsEarned = correctAnswers * 3
        earned_amount.text = "You have earned $coinsEarned coins"

        btn_finish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java)) }
    }
}

