package com.example.project.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        signUpButtonHome.setOnClickListener{
//            //The code of the button
//            Log.i("MainActivity", "Button Clicked")
//            Toast.makeText(this, "Button was clicked", Toast.LENGTH_SHORT).show()
//        }
        quizTest.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        signUpButtonHome.setOnClickListener{
            startActivity(Intent(this, SignUp::class.java))}

        signInButtonHome.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))}


    }}
