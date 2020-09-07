package com.learning.toukan.models

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter.writeString
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    var profileImage: String = "default",
    val tokens: Int = 0,
    val stagesCompleted: Int = 0,
    val stickersAmount: Int = 0,
    var stickers: List<SavedSticker> = listOf()
) : Parcelable

@Parcelize
data class SavedSticker(
    val id: String = "",
    var posx: Float = 0.5f,
    var posy: Float = 0.5f,
    var scale: Float = 0.5f,
    val timeadded: Timestamp? = null,
    val area: String = ""
) : Parcelable