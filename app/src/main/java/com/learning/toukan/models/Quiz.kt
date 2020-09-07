package com.learning.toukan.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quiz(
    @DocumentId val id: String = "",
    val stage: Int = 1,
    val title: String = "",
    val stageImage: String = "",
    val questions: List<Question> = listOf()
) :Parcelable

@Parcelize
data class Question(
    val title: String = "",
    val correct: Answer = Answer(),
    val image: String = "",
    val incorrect: MutableList<Answer> = mutableListOf(Answer(), Answer(), Answer()),
    @Exclude var correctPosition: Int = 0
    ):Parcelable

@Parcelize
data class Answer(
    val text: String = "",
    val key: String = ""
) : Parcelable