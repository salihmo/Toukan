package com.learning.toukan

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.learning.toukan.activities.SignInActivity
import com.learning.toukan.activities.SignUpActivity
import com.learning.toukan.models.Sticker
import com.learning.toukan.models.User
import com.google.firebase.Timestamp
import com.learning.toukan.models.SavedSticker
import kotlin.random.Random

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
    }
    fun updateImage(newImage: String) {
        val userDocument = mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.get().addOnSuccessListener { user ->
            val userProfilePic: String = (user["profileImage"] as String)
            if (userProfilePic.isNotEmpty())
                userDocument.update(
                    mapOf("profileImage" to newImage)
                )
        }
    }
    fun updateName(newName: String) {
        val userDocument = mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.get().addOnSuccessListener { user ->
            val userName: String = (user["name"] as String)
            if (userName.isNotEmpty())
                userDocument.update(
                    mapOf("name" to newName)
                )
        }
    }
    fun updateEmail(newEmail: String) {
        val userDocument = mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.get().addOnSuccessListener { user ->
            val userProfilePic: String = (user["email"] as String)
            if (userProfilePic.isNotEmpty())
                userDocument.update(
                    mapOf("email" to newEmail)
                )
        }
    }
    fun buySticker(context: Context, sticker: Sticker, callback: (Boolean) -> Unit) {
        val userDocument = mFirestore.collection(Constants.USERS).document(getCurrentUserID())
        val timestamp: Timestamp = Timestamp.now()
        userDocument.get().addOnSuccessListener { user ->
            val tokens: Long = user["tokens"] as Long
            val stickerAmount: Long = user["stickersAmount"] as Long
            val difference = sticker.cost - tokens
            if (tokens >= sticker.cost) {

                val stickerToAdd = SavedSticker(id = sticker.stickerId, posx = Random.nextFloat(), posy = Random.nextFloat(), scale = 0.2f, timeadded = timestamp)
                mFirestore.runBatch { batch ->
                    batch.update(userDocument, "tokens", (tokens - sticker.cost))
                    batch.update(userDocument, "stickersAmount", (stickerAmount + 1))
                    batch.update(
                        userDocument,
                        "stickers",
                        FieldValue.arrayUnion(stickerToAdd)
                    )
                }.addOnCompleteListener {
                    callback(true)
                }
            } else if (difference.toInt() != 1) {
                Toast.makeText(context, "You need $difference more tokens!", Toast.LENGTH_SHORT)
                    .show()
            } else
                Toast.makeText(context, "You need $difference more token!", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    fun updateStickers(newStickers: MutableList<*>) {
        val userDocument = mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.update(mapOf("stickers" to newStickers))
//        userDocument.get().addOnSuccessListener { user ->
//            val userName: MutableList<*> = (user["stickers"] as MutableList<*>)
//            if (userName.isNotEmpty())
//
//                )
//        }
    }

    fun deleteSticker(savedSticker: SavedSticker) {
        val userDocument = mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
        userDocument.update("stickers", FieldValue.arrayRemove(savedSticker))
       userDocument.update("stickersAmount", FieldValue.increment(-1))
    }








//            val stickerSelected = mFirestore.collection(Constants.STICKERS)
//                .document(sticker.stickerId)
//            userDocument.get().addOnSuccessListener {
//                if (it != null) {
//                    val sticker = it.toObject(Sticker::class.java)
//                }
//                val stickerCost: Long = ((sticker["cost"] as Long + add) * -1)
//                callback(stickerCost)
//                if (tokens >= stickerCost) {
//                    userDocument.update(mapOf("cost" to tokens))
//                }


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

