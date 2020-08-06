package com.learning.toukan.activities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.learning.toukan.Constants
import com.learning.toukan.models.Quiz
import com.learning.toukan.models.User

class ActivityViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val _user: MutableLiveData<User> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val user: LiveData<User> = _user

    init {
        val currentUserId = auth.currentUser?.uid
        if (currentUserId != null) {
            firestore.collection(Constants.USERS)
                .document(currentUserId)
                .addSnapshotListener { value, error ->
                    if (value != null && value.exists()) {
                        val loggedInUser = value.toObject(User::class.java)!!
                        _user.postValue(loggedInUser)
                    }
                    loading.postValue(false)
                }

            }

//            firestore.collection(Constants.USERS)
//                .document(currentUserId)
//                .get()
//                .addOnSuccessListener { document ->
//                    val loggedInUser = document.toObject(User::class.java)!!
//                    _user.postValue(loggedInUser)
//                }
//                .addOnFailureListener { e->
//
//                }
        }
    }
