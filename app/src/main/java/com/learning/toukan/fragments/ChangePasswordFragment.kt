package com.learning.toukan.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.learning.toukan.R
import com.learning.toukan.activities.SignInActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_change_password.passwordConfirmEdit


class ChangePasswordFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changePasswordButton.setOnClickListener {
            changePassword()
        }
        back_change_password_button.setOnClickListener {
//            val backToSettings = ActionOnlyNavDirections(R.id.action_back_to_settings)
            activity?.onBackPressed()
        }
    }

    val auth = FirebaseAuth.getInstance()
//        val newPassword = new_password.text.toString().trim()
//        val confirmNewPassword = passwordConfirmEdit.text.toString().trim()
//        val currentPassword = current_password.text.toString().trim()

    fun changePassword() {
        if (TextUtils.isEmpty(current_password.text)) {
            Toast.makeText(context, "Enter your current password", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(new_password.text)) {
            Toast.makeText(context, "Enter your new password", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(passwordConfirmEdit.text)) {
            Toast.makeText(context, "Please confirm your new password", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (new_password.text.toString().equals(passwordConfirmEdit.text.toString())) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null && currentUser.email != null) {
                val credentials =
                    EmailAuthProvider.getCredential(
                        currentUser.email!!,
                        current_password.text.toString()
                    )

                currentUser?.reauthenticate(credentials)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Your password has been changed.", Toast.LENGTH_SHORT).show()

                        currentUser?.updatePassword(new_password.text.toString())
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Please login with your new password.", Toast.LENGTH_LONG
                                    ).show()
                                    auth.signOut()
                                    val intent = Intent(activity, SignInActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                }
                            }

                    } else {
                        Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(context, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Make sure everything is filled out.", Toast.LENGTH_SHORT).show()
        }
    }


        }
//        toolbar_signUp.setNavigationOnClickListener { onBackPressed()
//            overridePendingTransition(R.anim.in_left_slide, R.anim.out_slide_right)





