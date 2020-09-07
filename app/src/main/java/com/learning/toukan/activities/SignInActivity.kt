package com.learning.toukan.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.learning.toukan.R
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        setContentView(R.layout.activity_sign_in)

        signInButton.setOnClickListener {
            signInUser() }



        forgotPasswordButton.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Reset password")
            builder.setMessage("Please enter the email address registered to the account, a link to reset your password will be sent.")
            val view = layoutInflater.inflate(R.layout.forgot_password_dialog, null)
            val email = view.findViewById<EditText>(R.id.et_email)
            builder.setView(view)
            builder.setIcon(R.drawable.revive)
            builder.setPositiveButton("Send", DialogInterface.OnClickListener { _, _ ->
                resetPassword(email)
            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ ->  })
            builder.show()
        }
        not_a_member.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
    fun signInSucess(user: com.learning.toukan.models.User){
        hideProgressDialog()
        startActivity(Intent(this, MainActivity:: class.java))
        finish()
    }
    private fun resetPassword(email: EditText) {
        if (TextUtils.isEmpty(email.text.toString())) {
            showSnack("Enter your email address")
            return
        } else {
            auth.sendPasswordResetEmail(email.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Email sent.", Toast.LENGTH_SHORT).show()
                    } else {
                      showSnack("No account has been found with that email address.")
                    }

                }
        }
    }

//    private fun setActionBar() {
//        setSupportActionBar(toolbar_signIn)
//
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.back_arrow)
//        }
//        toolbar_signIn.setNavigationOnClickListener { onBackPressed() }
//    }

    private fun signInUser(){
        val email: String = emailEditSignIn.text.toString().trim()
        val password: String = passwordEditSignIn.text.toString().trim()

        if(validateForm(email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        Log.d("Sign in", "createUserWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, MainActivity:: class.java))
                        Toast.makeText(this, "Let's learn some Mandarin" + "!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Sign in", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "No account with the details entered has been found.",
                            Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun validateForm(email: String, password: String) : Boolean{
        return if (TextUtils.isEmpty(email)){
                showSnack("Enter your email address")
                false
            } else if
        (TextUtils.isEmpty(password)){
                showSnack("Enter your password")
                false
            }else {
                true
            }

        }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
    }
