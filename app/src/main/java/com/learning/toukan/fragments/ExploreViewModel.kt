package com.learning.toukan.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.learning.toukan.Constants
import com.learning.toukan.models.Quiz

class ExploreViewModel: ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val _quizzes: MutableLiveData<List<Pair<Quiz, Boolean>>> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val quizzes: LiveData<List<Pair<Quiz, Boolean>>> = _quizzes

    var usersStage: Int = 0
        set(value) {
            field = value
            val quizCollection = firestore.collection(Constants.QUIZZES).orderBy("stage")
            quizCollection.get().addOnSuccessListener { result ->
                val quizzes = mutableListOf<Pair<Quiz, Boolean>>()
                for (quiz in result) {
                    val q = quiz.toObject(Quiz::class.java)
                    quizzes.add(Pair(q, (q.stage <= usersStage + 1)))
                }
                _quizzes.postValue(quizzes)
            }
        }

}