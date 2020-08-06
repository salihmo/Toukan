package com.learning.toukan.activities

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.learning.toukan.FirestoreClass
import com.learning.toukan.R
import com.learning.toukan.models.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.in_right_slide, R.anim.out_slide_left)
        setContentView(R.layout.activity_sign_up)
        setActionBar()

        signUpButton.setOnClickListener {
            registerTheUser()
        }
    }

    fun userRegisteredSuccess(){
        Toast.makeText(this@SignUpActivity, "You have successfully registered", Toast.LENGTH_SHORT).show()
        hideProgressDialog()
        FirebaseAuth.getInstance().signOut()
        finish()
        overridePendingTransition(R.anim.in_left_slide, R.anim.out_slide_right)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.in_left_slide, R.anim.out_slide_right)
    }

    private fun setActionBar() {
        setSupportActionBar(toolbar_signUp)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_arrow)
            actionBar.setTitle("")
        }

        toolbar_signUp.setNavigationOnClickListener {
            onBackPressed()
            overridePendingTransition(R.anim.in_left_slide, R.anim.out_slide_right)
        }

    }

    private fun registerTheUser() {
        val name: String = nameEdit.text.toString().trim() { it <= ' ' }
        val email: String = emailEdit.text.toString().trim() { it <= ' ' }
        val password: String = passwordEdit.text.toString().trim() { it <= ' ' }

        if (validateForm(name, email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        val registeredEmailAddress = firebaseUser.email!!

                        val user = User(firebaseUser.uid, name, registeredEmailAddress)

                        FirestoreClass().registerUser(this@SignUpActivity, user)

                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Registration failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
    private fun validateForm(name: String, email: String, password: String) : Boolean{
    return when {
        TextUtils.isEmpty(name)->{
            showSnack("Please enter your name")
            false
        }
        TextUtils.isEmpty(email)->{
            showSnack("Please enter your email address")
            false
        }
        TextUtils.isEmpty(password)->{
            showSnack("Please enter your password")
            false
        }
        passwordEdit.text.toString() != passwordConfirmEdit.text.toString() -> {
            showSnack("The passwords entered do not match")
            false
        }
        else -> {
           return true

        }
    }
    }
}