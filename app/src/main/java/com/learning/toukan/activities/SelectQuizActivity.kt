package com.learning.toukan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learning.toukan.R
import kotlinx.android.synthetic.main.activity_select_quiz.*

class SelectQuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_quiz)

        btn_test_quiz.setOnClickListener {
            launchQuiz("tgMYoF6Ye3RfkeQ0yTIf")
        }
        btn_second_quiz.setOnClickListener {
            launchQuiz("P4z5QTQCOQpfxbCuHZkj")
        }
    }

    fun launchQuiz(id: String) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("quiz_id", id)
        startActivity(intent)
    }
}