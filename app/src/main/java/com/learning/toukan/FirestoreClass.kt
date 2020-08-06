package com.learning.toukan

import android.app.Activity
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

    fun signInUser(activity: Activity) {
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.e(activity.javaClass.simpleName, document.toString())

                val loggedInUser = document.toObject(User::class.java)!!
                when (activity) {
                    is SignInActivity -> {
                        activity.signInSucess(loggedInUser)
                    }
                }
            }
            .addOnFailureListener { e ->
                when (activity) {
                    is SignInActivity -> {
                        activity.hideProgressDialog()
                    }
                }
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
        val userDocument = mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.get().addOnSuccessListener { user ->
            val tokens: Long = user["tokens"] as Long
            callback(tokens)
        }
    }


    fun updateTokens(add: Int, callback: (Long) -> Unit) {
        val userDocument = mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.get().addOnSuccessListener { user ->
            val tokens: Long = user["tokens"] as Long + add
            callback(tokens)
            userDocument.update(
                mapOf(
                    "tokens" to tokens
                )
            )
        }
    }

    fun getStage(callback: (Long) -> Unit) {
        val userDocument = mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.get().addOnSuccessListener { user ->
            val stage: Long = user["stagesCompleted"] as Long
            callback(stage)
        }
    }

    fun updateStage(newStage: Int) {
        val userDocument = mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.get().addOnSuccessListener { user ->
            val stage: Int = (user["stagesCompleted"] as Long).toInt()
            if (newStage > stage) {
                userDocument.update(
                    mapOf(
                        "stagesCompleted" to newStage
                    )
                )
            }
        }
    } fun updateImage(newImage: String){
        val userDocument = mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.get().addOnSuccessListener { user ->
            val userProfilePic: String = (user["profileImage"] as String)
            if (userProfilePic.isNotEmpty())
            userDocument.update(
                mapOf("profileImage" to newImage)
            )
        }

    } fun updateName(newName: String){
        val userDocument = mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.get().addOnSuccessListener { user ->
            val userName: String = (user["name"] as String)
            if (userName.isNotEmpty())
                userDocument.update(
                    mapOf("name" to newName)
                )
        }

    } fun updateEmail(newEmail: String){
        val userDocument = mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.get().addOnSuccessListener { user ->
            val userProfilePic: String = (user["email"] as String)
            if (userProfilePic.isNotEmpty())
                userDocument.update(
                    mapOf("email" to newEmail)
                )
        }

//    } fun updateImage(add: String){
//        val userDocument = mFirestore.collection(Constants.USERS)
//            .document(getCurrentUserID())
//        userDocument.get().addOnSuccessListener { user ->
//            val userProfilePic: String = (user["profileImage"] as String + add)
//            userDocument.update(
//                mapOf("profileImage" to userProfilePic)
    }


//    fun getQuizStage(){
//        mFirestore.collection(Constants.QUIZZES).addSnapshotListener {
//            snapshot, e ->
//            if (e != null) {
//            Log.w(TAG, "Listen Failed", e)
//                return@addSnapshotListener
//        }
//            if (snapshot != null) {
//                val quizStage: Long = quizzes.data.get("stage") as Long

}





