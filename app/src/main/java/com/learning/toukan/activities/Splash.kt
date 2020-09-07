package com.learning.toukan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.learning.toukan.FirestoreClass
import com.learning.toukan.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        overridePendingTransition(R.anim.in_right_slide, R.anim.out_slide_left)
        Handler(Looper.myLooper()!!).postDelayed({

            var currentUserID = FirestoreClass().getCurrentUserID()
            if (currentUserID.isNotEmpty()) {
                overridePendingTransition(R.anim.in_right_slide, R.anim.out_slide_left)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, SignInActivity::class.java))
            }
            finish()
        }, 2700)
    }
}