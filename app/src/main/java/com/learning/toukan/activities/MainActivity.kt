package com.learning.toukan.activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.learning.toukan.R

class MainActivity : BaseActivity() {

    private val activityViewModel: ActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        //a fade out animation, ensuring smooth transition
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        super.onCreate(savedInstanceState)
        //setting content to bottom navigation, essentially connecting it to the fragments
        setContentView(R.layout.activity_bottom_navigation)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        activityViewModel.loading.observe(this, Observer { loading ->
            if (loading) {
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

    private fun playSound(id: String): MediaPlayer? {
        return when (id) {
            "frog".toString() -> MediaPlayer.create(this, R.raw.cat_sound)
            else -> MediaPlayer.create(this, R.raw.hello_sound)
        }
    }
}