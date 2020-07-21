package com.learning.toukan.activities

import android.content.Intent
import android.os.Bundle
import com.learning.toukan.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        signUpButtonHome.setOnClickListener{
//            //The code of the button
//            Log.i("MainActivity", "Button Clicked")
//            Toast.makeText(this, "Button was clicked", Toast.LENGTH_SHORT).show()
//        }
        quizTest.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        signUpButtonHome.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))}

        signInButtonHome.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))}

    }}