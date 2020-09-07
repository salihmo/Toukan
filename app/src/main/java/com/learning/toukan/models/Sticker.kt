package com.learning.toukan.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sticker(
    @DocumentId val stickerId: String = "",
    val stickerName: String = "",
    val pinyin: String = "",
    val character: String = "",
    val english: String = "",
    val stickerImage: String = "",
    val key: String = "",
    val category: String = "",
    val cost: Int = 0
) : Parcelable

