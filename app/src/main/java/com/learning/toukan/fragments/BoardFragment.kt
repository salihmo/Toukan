package com.learning.toukan.fragments

import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.toAndroidPair
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.learning.toukan.BoardViewModel
import com.learning.toukan.Constants
import com.learning.toukan.FirestoreClass
import com.learning.toukan.R
import com.learning.toukan.activities.ActivityViewModel
import com.learning.toukan.entity.ImageEntity
import com.learning.toukan.entity.MotionEntity
import com.learning.toukan.models.SavedSticker
import com.learning.toukan.stickers.MotionView
import com.learning.toukan.view.Layer
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import kotlinx.android.synthetic.main.fragment_board.*
import kotlinx.android.synthetic.main.sticker_item.*


class BoardFragment : Fragment(), MotionView.MotionViewCallback {

    var mediaPlayer: MediaPlayer? = null
    private val viewModel: BoardViewModel by viewModels()
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val loader = ImageLoader.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false)
    }


    private lateinit var motionView: MotionView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




                val config = ImageLoaderConfiguration.Builder(requireContext())
            .memoryCache(LruMemoryCache(2 * 1024 * 1024)).build()
        loader.init(config)

        motionView = requireView().findViewById<MotionView>(R.id.motion_view)
        motionView.setMotionViewCallback(this)

        activityViewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.updateStickers(it)
            }
        })

        save_board_button.setOnClickListener {
            save()
            save_board_button.setVisibility(View.INVISIBLE)
        }

        delete_board_button.setOnClickListener {
            delete()
            delete_board_button.visibility = View.GONE
        }


//        if (sticker_board_pinyin.text.length >= 7 ){
//            sticker_board_pinyin.setTextSize(22F)
//        } else sticker_board_pinyin.setTextSize(30F)
//
//        if (sticker_board_english.text.length >= 8 ){
//            sticker_board_english.setTextSize(22F)
//        } else sticker_board_english.setTextSize(30F)
//
//
//        if (sticker_board_character.text.length >= 3 ){
//            sticker_board_character.setTextSize(22F)
//        } else sticker_board_character.setTextSize(30F)






            viewModel.stickers.observe(viewLifecycleOwner, Observer {
                motionView.entities.clear()
                it?.let { stickers ->
                    for (sticker in stickers) {

                        loader.loadImage(
                            sticker.first.stickerImage,
                            object : SimpleImageLoadingListener() {
                                override fun onLoadingComplete(
                                    imageUri: String?,
                                    view: View?,
                                    loadedImage: Bitmap?
                                ) {
                                    motionView.post {
                                        val layer = Layer()
                                        val entity = ImageEntity(
                                            sticker.toAndroidPair(),
                                            layer,
                                            loadedImage!!,
                                            motionView.width,
                                            motionView.height
                                        )

                                        motionView.addEntityAndPosition(entity)
                                        entity.moveCenterTo(
                                            PointF(
                                                motionView.width * sticker.second.posx,
                                                motionView.height * sticker.second.posy
                                            )
                                        )
                                        entity.layer.scale =
                                            sticker.second.scale * (1f - 0.06f) + 0.06f
                                    }
                                }
                            })

//                    Glide.with(requireContext())
//                        .asBitmap()
//                        .load(sticker.first.stickerImage)
//                        .into(object : CustomTarget<Bitmap>() {
//                            override fun onLoadCleared(placeholder: Drawable?) {
//                            }
//
//                            override fun onResourceReady(
//                                resource: Bitmap,
//                                transition: Transition<in Bitmap>?
//                            ) {
//
//                            }
//                        })

                    }
                }
            })
        }

        fun save() {
            val savedStickers = mutableListOf<SavedSticker>()
            for (entity in motionView.entities) {
                val imageEntity = entity as ImageEntity
                val savedSticker = imageEntity.sticker.second

                val posX = imageEntity.absoluteCenterX() / motionView.width
                val posY = imageEntity.absoluteCenterY() / motionView.height
                val scale = (imageEntity.layer.scale - 0.06f) / (1f - 0.06f)

                savedSticker.posx = posX
                savedSticker.posy = posY
                savedSticker.scale = scale
                savedStickers.add(savedSticker)
            }

            FirestoreClass().updateStickers(savedStickers)

//        activityViewModel.user.observe(viewLifecycleOwner, Observer { user ->
//            user.stickers = savedStickers
//        })
        }

        fun delete() {

            val selected = motionView.selectedEntity as ImageEntity

            FirestoreClass().deleteSticker(selected.sticker.second)
        }

        override fun onEntitySelected(entity: MotionEntity?) {
            if (entity is ImageEntity) {
                save_board_button.setVisibility(View.VISIBLE)
                delete_board_button.setVisibility(View.VISIBLE)
                sticker_board_english.text = entity.sticker.first.english
                sticker_board_character.text = entity.sticker.first.character
                sticker_board_pinyin.text = entity.sticker.first.pinyin

                if (entity.sticker.first.pinyin.length >= 7) {
                    sticker_board_pinyin.textSize = 22f
                } else sticker_board_pinyin.textSize = 30f

                if (entity.sticker.first.character.length >= 3) {
                    sticker_board_character.textSize = 30f
                } else sticker_board_character.textSize = 50f

                if (entity.sticker.first.character.length >= 3){
                    sticker_board_character.textSize = 25f
                } else sticker_board_character.textSize = 50f

                if (entity.sticker.first.english.length >= 7){
                    sticker_board_english.textSize = 22f
                } else sticker_board_english.textSize = 30f




                mediaPlayer?.release()
                mediaPlayer = MediaPlayer.create(
                    context,
                    Constants.getSoundForId(entity.sticker.first.key)
                )
                mediaPlayer?.setOnPreparedListener {
                    it.start()
                }
            } else {
                delete_board_button.setVisibility(View.GONE)
            }
        }

        override fun onEntityDoubleTap(entity: MotionEntity) {

        }

    }


//
//sticker_image?.setOnClickListener {
//    if (sticker_board_pinyin.text.length > 10){
//        sticker_board_pinyin.setTextSize(20f)
//    }
//    else
//        sticker_board_pinyin.setTextSize(25f)
//}
//
//sticker_image?.setOnClickListener {
//    if (sticker_board_character.text.length > 3){
//        sticker_board_character.setTextSize(20f)
//    }
//    else
//        sticker_board_character.setTextSize(25f)
//}
//
//sticker_image?.setOnClickListener {
//    if (sticker_board_english.text.length > 10){
//        sticker_board_english.setTextSize(20f)
//    }
//    else
//        sticker_board_english.setTextSize(25f)
//}