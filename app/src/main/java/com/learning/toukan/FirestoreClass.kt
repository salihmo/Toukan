package com.learning.toukan

import android.media.session.MediaSessionManager
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.learning.toukan.activities.SignInActivity
import com.learning.toukan.activities.SignUpActivity
import com.learning.toukan.models.User

class FirestoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo: User) {
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error", e)
            }
    }


    fun signInUser(activity: SignInActivity) {
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)
                if (loggedInUser != null)
                    activity.signInSucess(loggedInUser)
            }.addOnFailureListener { e ->
                Log.e(activity.javaClass.simpleName, "Error", e)
            }
    }


    fun getCurrentUserID(): String {
        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
}

    fun getTokens(callback: (Long) -> Unit) {
        val userDocument =  mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.get().addOnSuccessListener { user ->
            val tokens: Long = user["tokens"] as Long
            callback(tokens)
        }
    }

    fun updateTokens(add: Int) {
        val userDocument =  mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.get().addOnSuccessListener { user ->
                val tokens: Long = user["tokens"] as Long + add
                userDocument.update(mapOf(
                    "tokens" to tokens
                ))
            }
    }

}