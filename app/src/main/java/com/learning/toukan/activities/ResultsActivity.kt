package com.learning.toukan.activities
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.learning.toukan.Constants
import com.learning.toukan.FirestoreClass
import com.learning.toukan.R
import com.learning.toukan.fragments.ExploreFragment
import com.learning.toukan.models.Quiz
import com.learning.toukan.models.User
import kotlinx.android.synthetic.main.results.*

class ResultsActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.results)

        val totalQuestions = intent.getIntExtra(Constants.totalQuestions, 0)
        val correctAnswers = intent.getIntExtra(Constants.totalCorrect, 0)
        val quiz: Quiz? = intent.getParcelableExtra(Constants.quiz)
        val userStage: User? = intent.getParcelableExtra(Constants.USERS)

        if (correctAnswers.toDouble() >= totalQuestions * 0.8) {
            val quizStage = quiz?.stage ?: 0
            FirestoreClass().updateStage(quizStage)
        }


        score_is.text = "Your score is $correctAnswers out of $totalQuestions"
        val coinsEarned = correctAnswers * 3
        earned_amount.text = "You have earned $coinsEarned coins"


        FirestoreClass().updateTokens(coinsEarned) { tokens->
            Toast.makeText(this, "You now have $tokens tokens", Toast.LENGTH_SHORT).show()
        }
       // FirestoreClass().getTokens {
       //     Toast.makeText(this, "You now have $it tokens", Toast.LENGTH_SHORT).show()
      //  }
        btn_finish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java)) }
    }
}