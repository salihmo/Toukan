package com.learning.toukan

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


object Constants{
    fun getImageForId(id: String): Int {
        return when(id) {
            "cat" -> R.drawable.cat
            "dog" -> R.drawable.dog
            else -> R.drawable.airport
        }
    }

    const val USERS: String = "users"

    const val totalQuestions: String = "total_question"
    const val totalCorrect: String = "total_correct"

    //suspend fun getQuestions(): List<Question> = withContext(Dispatchers.Default){
//        val questionsList = ArrayList<Question>()
//
//        val question1 = Question(1, "Please choose the correct pinyin for this character", R.drawable.wo, "Wǒ", "Guó ", "Ní", "Tā",1)
//
//        val question2 = Question(2, "How do you say France?", R.drawable.france, "Yīng guó", "Fǎ guó ", "É guó", "Zhōng guó",2)
//
//        val question3 = Question(3, "How many people are there at the park?", R.drawable.park, "yī", "bā ", "sì", "sān",4)
//
//        val question4 = Question(4, "How do you say Cat?", R.drawable.cat, "māo", "gǒu ", "jī", "shǔ",1)
//
//        val question5 = Question(5, "How do you say Dog?", R.drawable.dog, "shǔ", "jī ", "gǒu", "māo",3)
//
//        val question6 = Question(6, "Please choose the correct pinyin for this character?", R.drawable.guo, "ní", "xǐ huan ", "tā", "guó",4)
//
//        val question7 = Question(7, "How many birds are in this picture?", R.drawable.birds, "jiǔ", "wǔ", "shí", "èr",2)
//
//        val question8 = Question(8, "What does 'xièxie' mean in English? ", R.drawable.xiexie, "Please", "Goodbye", "Hello", "Thank you",4)
//
//        val question9 = Question(9, "How do you say Germany?", R.drawable.germany, "Dé guó", "Yīng guó", "Xī bān yá", "Rì běn",1)
//
//        val question10 = Question(10, "What is this colour?", R.drawable.red, "chéng sè", "lán sè", "hóng sè", "huáng sè",3)
//
//        questionsList.add(question1)
//        questionsList.add(question2)
//        questionsList.add(question3)
//        questionsList.add(question4)
//        questionsList.add(question5)
//        questionsList.add(question6)
//        questionsList.add(question7)
//        questionsList.add(question8)
//        questionsList.add(question9)
//        questionsList.add(question10)

    //    val db = FirebaseFirestore.getInstance()

    //    val quiz = db.collection("quizzes").document("tgMYoF6Ye3RfkeQ0yTIf")

    //    quiz.get().addOnSuccessListener {
     //       return@addOnSuccessListener listOf<Question>()
     //   }

        //Collections.shuffle(questionsList)
       // return questionsList

  //  }

}