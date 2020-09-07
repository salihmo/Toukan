package com.learning.toukan.fragments

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.learning.toukan.Constants
import com.learning.toukan.models.Quiz
import com.learning.toukan.models.Sticker
import java.util.*

class StoreViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    val loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val category: MutableLiveData<String> = MutableLiveData<String>("animals")

    val stickers: LiveData<List<Sticker>> = category.switchMap {
        val liveData: MutableLiveData<List<Sticker>> = MutableLiveData()
        val stickerCollection =
            firestore.collection(Constants.STICKERS).whereEqualTo("category", it)
        stickerCollection.get().addOnSuccessListener { result ->
            val stickers = mutableListOf<Sticker>()
            for (sticker in result) {
                val s = sticker.toObject(Sticker::class.java)
                stickers.add(s)
            }
            stickers.sortBy {
                it.pinyin.toUpperCase(Locale.ROOT)
            }
            stickers.sortBy {
                it.cost
        }
            liveData.postValue(stickers)
        }
        return@switchMap liveData
    }
}