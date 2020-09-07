package com.learning.toukan

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import com.google.firebase.firestore.FirebaseFirestore
import com.learning.toukan.activities.QuizActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


object Constants {
    fun getImageForId(id: String): Int {
        return when (id) {
            "cat" -> R.drawable.cat
            "dog" -> R.drawable.dog
            "four" -> R.drawable.fourtones
            "tones" -> R.drawable.four_tones
            "angry" -> R.drawable.angrycat
            "questions" -> R.drawable.questions_image
            "five" -> R.drawable.five_picture
            "risingsun" -> R.drawable.risingsun
            "park" -> R.drawable.park
            "park2" -> R.drawable.park2
            "fallingleaves" -> R.drawable.fallingleaves
            "fingerman" -> R.drawable.fingerman
            "doctorhe" -> R.drawable.doctorhe
            "she" -> R.drawable.she
            "jaguar" -> R.drawable.jaguar
            "profile" -> R.drawable.profiledefault
            "community" -> R.drawable.communitywe
            "doctorsheis" -> R.drawable.doctorsheis
            "conversation" -> R.drawable.conversation
            "familycartoon" -> R.drawable.familycartoon
            "thumbsdown" -> R.drawable.thumbsdown
            "team" -> R.drawable.team
            "sheis" -> R.drawable.sheis
            "communitywe" -> R.drawable.communitywe
            "restaurant" -> R.drawable.restaurant
            "phoneconvo" -> R.drawable.phoneconvo
            "meditate" -> R.drawable.meditate
            "familysilho" -> R.drawable.family_sil
            "chickenfamily" -> R.drawable.chickenfamily
            "likedislike" -> R.drawable.likedislike
            "angrysheep" -> R.drawable.angrysheep
            "thumbsup" -> R.drawable.thumbsup
            "homepic" -> R.drawable.homepic
            "familywave" -> R.drawable.familywave
            "groupimage" -> R.drawable.group_image
            else -> R.drawable.questions_image
        }
    }

    fun getSoundForId(id: String): Int {
        return when(id){
            "cat" -> R.raw.cat_sound
            "dog" -> R.raw.dog_sound
            "hello" -> R.raw.hello_sound
            "horse" -> R.raw.horse_sound
            "cow" -> R.raw.cow_sound
            "fish" -> R.raw.fish_sound
            "chicken" -> R.raw.chicken_sound
            "bird" -> R.raw.bird_sound
            "person" -> R.raw.human_sound
            "monkey" -> R.raw.monkey_sound
            "lion" -> R.raw.lion_sound
            "house" -> R.raw.house_sound
            "home" -> R.raw.home_sound
            "school" -> R.raw.school_sound
            "farm" -> R.raw.farm_sound
            "restaurant" -> R.raw.restaurant_sound
            "hospital" -> R.raw.hospital_sound
            "university" -> R.raw.university_sound
            "bank" -> R.raw.bank_sound
            "hotel" -> R.raw.hotel_sound
            "egg" -> R.raw.egg_sound
            "water" -> R.raw.water_sound
            "apple" -> R.raw.apple_sound
            "milk" -> R.raw.milk_sound
            "bread" -> R.raw.bread_sound
            "salad" -> R.raw.salad_sound
            "tea" -> R.raw.tea_sound
            "coffee" -> R.raw.coffee_sound
            "meat" -> R.raw.meat_sound
            "tissues" -> R.raw.tissues_sound
            "bag" -> R.raw.bag_sound
            "chair" -> R.raw.chair_sound
            "bed" -> R.raw.bed_sound
            "bathtub" -> R.raw.bathtub_sound
            "clock" -> R.raw.clock_sound
            "television" -> R.raw.teleivision_sound
            "mobile" -> R.raw.mobile_sound
            "computer" -> R.raw.computer_sound
            "musician" -> R.raw.musician_sound
            "tourist" -> R.raw.tourist_sound
            "teacher" -> R.raw.teacher_sound
            "chef" -> R.raw.chef_sound
            "doctor" -> R.raw.doctor_sound
            "manager" -> R.raw.manager_sound
            "lawyer" -> R.raw.lawyer_sound
            "student" -> R.raw.student_sound
            "scientist" -> R.raw.scientist_sound
            "bicycle" -> R.raw.bicycle_sound
            "boat" -> R.raw.boat_sound
            "taxi" -> R.raw.taxi_sound
            "motorbike" -> R.raw.motorbike_sound
            "bus" -> R.raw.bus_sound
            "car" -> R.raw.car_sound
            "train" -> R.raw.train_sound
            "ferry" -> R.raw.ferry_sound
            "aeroplane" -> R.raw.aeroplane_sound
            "hemp" -> R.raw.hemp_sound
            "mumma" -> R.raw.mumma_sound
            "scold" -> R.raw.scold_sound
            "me" -> R.raw.me_sound
            "de" -> R.raw.de_sound
            "country" -> R.raw.country_sound
            "not" -> R.raw.not_sound
            "like" -> R.raw.like_sound
            "littlesister" -> R.raw.littlesister_sound
            "friend" -> R.raw.friend_sound
            "thanks" -> R.raw.thanks_sound
            "mama" -> R.raw.mama_sound
            "you" -> R.raw.you_sound
            "she" -> R.raw.she_sound
            "he" -> R.raw.he_sound
            "it" -> R.raw.it_sound
            "plural" -> R.raw.plural_sound
            "we" -> R.raw.we_sound
            "youall" -> R.raw.youall_sound
            "they" -> R.raw.they_sound
            "is" -> R.raw.is_sound
            "my" -> R.raw.my_sound
            "his" -> R.raw.his_sound
            "hers" -> R.raw.hers_sound
            "yours" -> R.raw.yours_sound
            "theirs" -> R.raw.theirs_sound
            "yoursplural" -> R.raw.yoursplural_sound
            "ours" -> R.raw.ours_sound
            "iam" -> R.raw.iam_sound
            "iamnot" -> R.raw.iamnot_sound
            "youare" -> R.raw.youare_sound
            "youarenot" -> R.raw.youarenot_sound
            "theyare" -> R.raw.theyare_sound
            "theyarenot" -> R.raw.theyarenot_sound
            "weare" -> R.raw.weare_sound
            "wearenot" -> R.raw.wearenot_sound
            "sheisnot" -> R.raw.sheisnot_sound
            "iamnotgood" -> R.raw.iamnotgood_sound
            "myfriend" -> R.raw.myfriend_sound
            "heis" -> R.raw.heis_sound
            "sheis" -> R.raw.sheis_sound
            "itis" -> R.raw.itis_sound
            "heisnot" -> R.raw.heisnot_sound
            "iamgood" -> R.raw.iamgood_sound
            "itisnot" -> R.raw.itisnot_sound
            "hen" -> R.raw.hen_sound
            "question" -> R.raw.question_sound
            "youaregood" -> R.raw.youaregood_sound
            "igood" -> R.raw.igood_sound
            "imnotgood" -> R.raw.imnotgood_sound
            "yourfriendisgood" -> R.raw.yourfriendisgood_sound
            "yourfriend" -> R.raw.yourfriend_sound
            "ourfriend" -> R.raw.our_friend
            "ourfriendisgood" -> R.raw.ourfriendisgood_sound
            "howareyou" -> R.raw.howareyou_sound
            "howisyourfriend" -> R.raw.howisyourfriend_sound
            "love" -> R.raw.love_sound
            "bye" -> R.raw.bye_sound
            "iloveyou" -> R.raw.iloveyou_sound
            "idontloveyou" -> R.raw.idontloveyou_sound
            "ilikeyou" -> R.raw.ilikeyou_sound
            "idontlikeyou" -> R.raw.idontlikeyou_sound
            "weloveyou" -> R.raw.weloveyou_sound
            "ilovemyfriend" -> R.raw.ilovemyfriend_sound
            "ilikemyfriend" -> R.raw.ilikemyfriend_sound
            "idontlikeyourfriend" -> R.raw.idontlikeyourfriend_sound
            "ihateyou" -> R.raw.ihateyou_sound
            "wedonthateyou" -> R.raw.wedonthateyou_sound
            "hehatesme" -> R.raw.hehatesme_sound
            "imannoyedbyyou" -> R.raw.imannoyedbyyou_sound
            "helovesourfriend" -> R.raw.helovesourfriend_sound
            "theyloveourfriend" -> R.raw.theyloveourfriend_sound
            "myhome" -> R.raw.myhome_sound
            "thisisnotmyolderbrother" -> R.raw.thisisnotmyolderbrother_sound
            "thisisnotmydadsfriend" -> R.raw.thisisnotmydadsfriend_sound
            "thisismymother" -> R.raw.thisismymother_sound
            "thisismyfamily" -> R.raw.thisismyfamily_sound
            "thisisnotmyhome" -> R.raw.thisisnotmyhome_sound
            "thisismyhome" -> R.raw.thisismyhome_sound
            "littlebrother" -> R.raw.youngerbrother_sound
            "olderbrother" -> R.raw.olderbrother_sound
            "oldersister" -> R.raw.oldersister_sound
            "baba" -> R.raw.baba_sound
            "mybestfriend" -> R.raw.mybestfriend_sound
            "mybestfriendshome" -> R.raw.mybestfriendshome_sound
            "myfamily" -> R.raw.myfamily_sound
            "thisismybestfriendshome" -> R.raw.thisismybestfriendshome_sound
            "one" -> R.raw.one_sound
            "two" -> R.raw.two_sound
            "three" -> R.raw.three_sound
            "four" -> R.raw.four_sound
            else -> R.raw.hello_sound
        }
    }

    const val quiz: String = "quiz"
    const val USERS: String = "users"
    const val STICKERS: String = "stickers"
    const val QUIZZES: String = "quizzes"
    const val totalQuestions: String = "total_question"
    const val totalCorrect: String = "total_correct"

}



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

