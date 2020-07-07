package com.example.project.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setActionBar()
    }

    private fun setActionBar(){
        setSupportActionBar(toolbar_signUp)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_arrow)
        }

        toolbar_signUp.setNavigationOnClickListener { onBackPressed() }

    }
}