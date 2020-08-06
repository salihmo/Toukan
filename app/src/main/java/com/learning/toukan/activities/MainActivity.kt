package com.learning.toukan.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseUser
import com.learning.toukan.R
import com.learning.toukan.models.User

class MainActivity : BaseActivity() {

    private val activityViewModel: ActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)

        showSnack("Let's learn some Mandarin!")
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        activityViewModel.loading.observe(this, Observer { loading->
            if(loading) {
                showProgressDialog("Loading")
            } else {
                hideProgressDialog()
            }
        })
    }

    fun launchQuiz(id: String) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("quiz_id", id)
        startActivity(intent)
    }


}