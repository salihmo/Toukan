package com.learning.toukan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.learning.toukan.activities.MainActivity
import com.learning.toukan.fragments.BoardFragment
import com.learning.toukan.models.SavedSticker
import com.learning.toukan.models.Sticker
import com.learning.toukan.models.User
import java.security.AccessController.getContext


class BoardViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    val stickers: MutableLiveData<List<Pair<Sticker, SavedSticker>>> = MutableLiveData()

    fun updateStickers(user: User) {
        val tasks: MutableList<Task<DocumentSnapshot>> = mutableListOf()
        for (savedSticker in user.stickers) {
            val documentSnapshotTask: Task<DocumentSnapshot> =
                firestore.collection("stickers").document(savedSticker.id).get()
            tasks.add(documentSnapshotTask)
        }
        Tasks.whenAllSuccess<DocumentSnapshot>(tasks)
            .addOnSuccessListener { documents ->
                val stickersOwnedByUser = mutableListOf<Pair<Sticker, SavedSticker>>()
                for (stickerDocument in documents) {
                    val s = stickerDocument.toObject(Sticker::class.java)
                    s?.let { sticker ->
                        val savedSticker = user.stickers.find { it.id == sticker.stickerId } ?: return@let
                        stickersOwnedByUser.add(Pair(sticker, savedSticker))
                    }
                }
                // remove all that aren't in category and only post ones that are
                stickers.postValue(stickersOwnedByUser)
            }
    }

 //   init {
//        Log.i("Board", "Starting BoardViewModel")
//        val currentUserId = auth.currentUser?.uid
//        if (currentUserId != null) {
//            Log.i("Board", "Starting request")
//            firestore.collection(Constants.USERS).document(currentUserId).get()
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val document: DocumentSnapshot? = task.getResult()
//                        if (document != null) {
//                            if (document.exists()) {
//                                val user = document.toObject(User::class.java) ?: return@addOnCompleteListener
//
//                                Tasks.whenAllSuccess<DocumentSnapshot>(tasks).addOnSuccessListener {
//                                    OnSuccessListener<List<Sticker>> {
//                                        fun onSuccess(list: List<Any>, url: String, view: Any) {
//                                            if (url.isNotEmpty()) {
//                                                val stickerImage =
//                                                    firestore.collection(Constants.STICKERS)
//                                                        .whereEqualTo("stickerImage", it)
//
//                                            }
//                                            for (`object` in list) {
//                                                val s: Sticker? =
//                                                    (`object` as DocumentSnapshot).toObject(
//                                                        Sticker::class.java
//                                                    )
//                                                if (s != null) {
//                                                    Log.d("TAG", s.stickerName)
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
                      //      }
                      //  }
                 //   }
              //  }
       // }
  //  }
}
