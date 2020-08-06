package com.learning.toukan.models

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter.writeString

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    var profileImage: String = "",
    val tokens: Int = 0,
    val fcmToken: String = "",
    val stagesCompleted: Int = 0,
    val stickersAmount: Int = 0



) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readInt(),
        source.readString()!!,
        source.readInt(),
        source.readInt()

    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeString(email)
        writeString(profileImage)
        writeInt(tokens)
        writeString(fcmToken)
        writeInt(stagesCompleted)
        writeInt (stickersAmount )

    }
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}