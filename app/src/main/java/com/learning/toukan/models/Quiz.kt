package com.learning.toukan.models

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quiz(
    @DocumentId val id: String = "",
    val stage: Int = 1,
    val title: String = "",
    val questions: List<Questions> = listOf()
) :Parcelable

@Parcelize
data class Questions(
     val title: String = "",
     val correct: String = "",
     val image: String = "",
     val incorrect: List<String> = listOf("", "" ,"")
    ):Parcelable