package com.learning.toukan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.learning.toukan.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_sign_in)

        signInButton.setOnClickListener {
            signInUser() }

        setActionBar()
    }

    fun signInSucess(user: com.learning.toukan.models.User){
        hideProgressDialog()
        startActivity(Intent(this, SelectQuizActivity:: class.java))
        finish()
    }


    private fun setActionBar() {
        setSupportActionBar(toolbar_signIn)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_arrow)
        }
        toolbar_signIn.setNavigationOnClickListener { onBackPressed() }
    }

    private fun signInUser(){
        val email: String = emailEditSignIn.text.toString().trim()
        val password: String = passwordEditSignIn.text.toString().trim()

        if(validateForm(email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Sign in", "createUserWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, SelectQuizActivity:: class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Sign in", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun validateForm(email: String, password: String) : Boolean{
        return if (TextUtils.isEmpty(email)){
                showErrorSnack("Enter your email address")
                false
            } else if
        (TextUtils.isEmpty(password)){
                showErrorSnack("Enter your password")
                false
            }else {
                true
            }
        }}