package com.learning.toukan.activities
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.FragmentNavigator
import com.learning.toukan.Constants
import com.learning.toukan.FirestoreClass
import com.learning.toukan.R
import com.learning.toukan.fragments.ExploreFragment
import com.learning.toukan.models.Quiz
import com.learning.toukan.models.User
import kotlinx.android.synthetic.main.results.*

class LessonResultActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.in_right_slide, R.anim.out_slide_left)
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

        if (correctAnswers.toDouble() >= 1 * totalQuestions) {
            score_is.text =
                "Full marks! $correctAnswers out of $totalQuestions! That's fantastic! Carry on and you'll be a pro in no time!"
        } else if (correctAnswers.toDouble() >= totalQuestions * 0.8) {
            score_is.text =
                "You did great, $correctAnswers out of $totalQuestions! Nearly full marks!"
        } else if (correctAnswers.toDouble() >= totalQuestions * 0.5) {
            score_is.text =
                "$correctAnswers out of $totalQuestions means you're making great progress, you can do this!"
        } else if (correctAnswers.toDouble() <= totalQuestions * 0.5) {
            score_is.text =
                "$correctAnswers out of $totalQuestions, just keep practicing!"
        } else if (correctAnswers.toDouble() <= totalQuestions * 0) {
            score_is.text =
                "$correctAnswers out of $totalQuestions, take the lesson a few more times and you'll do great."
        } else score_is.text =
            "$correctAnswers out of $totalQuestions, take the lesson a few more times and you'll do great."


        val coinsEarned = correctAnswers * 1
        if (coinsEarned != 0 && coinsEarned != 1) {
            earned_amount.text =
                "You have earned $coinsEarned tokens! Redeem them at the sticker store!" }
            else if (coinsEarned == 1){
            earned_amount.text = "You earned $coinsEarned token!"
            } else { earned_amount.text = "No tokens earned this time!"
    }

        FirestoreClass().updateTokens(coinsEarned) { tokens ->
            Toast.makeText(this, "You now have $tokens tokens", Toast.LENGTH_SHORT).show()
        }
        btn_finish.setOnClickListener {
            finish()
        }
    }
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.in_right_slide, R.anim.out_slide_left)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}