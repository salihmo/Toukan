package com.learning.toukan.activities
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirestoreRegistrar
import com.learning.toukan.Constants
import com.learning.toukan.FirestoreClass
import com.learning.toukan.R
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

        FirestoreClass().updateTokens(coinsEarned)
        FirestoreClass().getTokens {
            Toast.makeText(this, "You now have $it tokens", Toast.LENGTH_SHORT).show()
        }
        
        btn_finish.setOnClickListener{
            startActivity(Intent(this, SelectQuizActivity::class.java)) }
    }
}