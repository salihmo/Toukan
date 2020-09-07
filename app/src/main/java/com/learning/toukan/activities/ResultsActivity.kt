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

class ResultsActivity : AppCompatActivity() {
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
                "Full marks! $correctAnswers out of $totalQuestions! That's fantastic! You did great at that stage!"
        } else if (correctAnswers.toDouble() >= totalQuestions * 0.8) {
            score_is.text =
                "You did great, $correctAnswers out of $totalQuestions! Nearly full marks! You're fantastic!"
        } else if (correctAnswers.toDouble() >= totalQuestions * 0.5) {
            score_is.text =
                "$correctAnswers out of $totalQuestions means you're making great progress, you're awesome!"
        } else if (correctAnswers.toDouble() <= totalQuestions * 0.5) {
            score_is.text =
                "$correctAnswers out of $totalQuestions, just keep practicing!"
        } else if (correctAnswers.toDouble() <= totalQuestions * 0) {
            score_is.text =
                "$correctAnswers out of $totalQuestions, take the stage a few more times and you'll do great."
        } else score_is.text =
            "$correctAnswers out of $totalQuestions, no problem you'll do great next time."


        val coinsEarned = correctAnswers * 3
        earned_amount.text = "You have earned $coinsEarned tokens"

        FirestoreClass().updateTokens(coinsEarned) { tokens ->
            Toast.makeText(this, "You now have $tokens tokens", Toast.LENGTH_SHORT).show()
        }
        // FirestoreClass().getTokens {
        //     Toast.makeText(this, "You now have $it tokens", Toast.LENGTH_SHORT).show()
        //  }
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