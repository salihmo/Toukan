package com.learning.toukan.fragments

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.learning.toukan.Constants
import com.learning.toukan.FirestoreClass
import com.learning.toukan.R
import com.learning.toukan.activities.ActivityViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_store.*
import java.util.*


class StoreFragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val viewModel: StoreViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store, container, false)
    }



    private lateinit var stickerAdapter: StickerAdapter
    fun buySticker() {
        val position = stickerAdapter.selectedPosition
        if (position >= 0) {
            val sticker = stickerAdapter.currentList.get(position)
        }
    }
    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mediaPlayer?.prepare()
        val shake: Animation = AnimationUtils.loadAnimation(context, R.anim.shake)
        val tokenrotation: Animation = AnimationUtils.loadAnimation(context, R.anim.rotationtoken)
        super.onViewCreated(view, savedInstanceState)
        stickerAdapter = StickerAdapter(Glide.with(this)) { sticker, position ->
            val oldSelected = stickerAdapter.selectedPosition
            stickerAdapter.selectedPosition = position
            stickerAdapter.selectedPosition
            stickerAdapter.notifyItemChanged(position)


            item_selected_english.text = sticker.english
            buy_sticker.setOnClickListener {
                context?.let { it1 ->
                    FirestoreClass().buySticker(it1, sticker) {
                        Toast.makeText(requireContext(), "Sticker purchased!", Toast.LENGTH_SHORT)
                            .show()
                        buy_sticker.startAnimation(shake)
                        store_token_image.startAnimation(tokenrotation)
                        mediaPlayer?.release()
                        mediaPlayer = MediaPlayer.create(context, R.raw.spending_sound)
                        mediaPlayer?.start()
                        shopkeeper.playAnimation()
                    }
                }
            }
        }
        val layoutManager = GridLayoutManager(context, 3)
        sticker_list_rv.layoutManager = layoutManager
        sticker_list_rv.adapter = stickerAdapter

        viewModel.stickers.observe(viewLifecycleOwner, Observer {
            stickerAdapter.selectedPosition = -1
            stickerAdapter.submitList(it)

        })

        store_token_image.setOnClickListener {
            Toast.makeText(requireContext(), "Spend tokens and get stickers!", Toast.LENGTH_SHORT).show()
            store_token_image.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.rotationtoken))
        }

        store_animals_button.setOnClickListener {
            viewModel.category.value = "animals"
        }
        store_buildings_button.setOnClickListener {
            viewModel.category.value = "buildings"
        }
        store_food_button.setOnClickListener {
            viewModel.category.value = "food"
        }
        store_items_button.setOnClickListener {
            viewModel.category.value = "items"
        }
        store_people_button.setOnClickListener {
            viewModel.category.value = "people"
        }
        store_vehicles_button.setOnClickListener {
            viewModel.category.value = "vehicles"
        }
        viewModel.category.observe(viewLifecycleOwner, Observer {
            val matrix = ColorMatrix()
            matrix.setSaturation(0f)
            val cf = ColorMatrixColorFilter(matrix)
            if (it == "food") {
                store_title.text = "Food & Drink"
                store_title.textSize = 27F
            } else {
                store_title.text = "$it".capitalize(Locale.ROOT)
                store_title.textSize = 30F
            }
            if (it == "animals") {
                shopkeeper.setAnimation(R.raw.bear_animation)
                shopkeeper.playAnimation()
                store_animals_button.setColorFilter(cf)
                store_animals_button.setImageAlpha(128)
            } else if (it != "animals") {
                store_animals_button.setColorFilter(null)
                store_animals_button.setImageAlpha(255)
            }
            if (it == "buildings") {
                shopkeeper.setAnimation(R.raw.monkey_animation)
                shopkeeper.playAnimation()
                store_buildings_button.setColorFilter(cf)
                store_buildings_button.setImageAlpha(128)
            } else if (it != "buildings") {
                store_buildings_button.setColorFilter(null)
                store_buildings_button.setImageAlpha(255)
            }
            if (it == "food") {
                shopkeeper.setAnimation(R.raw.cookiechef_animation)
                shopkeeper.playAnimation()
                store_food_button.setColorFilter(cf)
                store_food_button.setImageAlpha(128)
            } else if (it != "food") {
                store_food_button.setColorFilter(null)
                store_food_button.setImageAlpha(255)
            }

            if (it == "people") {
                shopkeeper.setAnimation(R.raw.star_animation)
                shopkeeper.playAnimation()
                store_people_button.setColorFilter(cf)
                store_people_button.setImageAlpha(128)
            } else if (it != "people") {
                store_people_button.setColorFilter(null)
                store_people_button.setImageAlpha(255)
            }

            if (it == "items") {
                shopkeeper.setAnimation(R.raw.robot_animation)
                shopkeeper.playAnimation()
                store_items_button.setColorFilter(cf)
                store_items_button.setImageAlpha(128)
            } else if (it != "items") {
                store_items_button.setColorFilter(null)
                store_items_button.setImageAlpha(255)
            }
            if (it == "vehicles") {
                shopkeeper.setAnimation(R.raw.bikeman_animation)
                shopkeeper.playAnimation()
                store_vehicles_button.setColorFilter(cf)
                store_vehicles_button.setImageAlpha(128)
            } else if (it != "vehicles") {
                store_vehicles_button.setColorFilter(null)
                store_vehicles_button.setImageAlpha(255)
            }

            store_animals_button.isEnabled = true
            store_food_button.isEnabled = true
            store_items_button.isEnabled = true
            store_buildings_button.isEnabled = true
            store_people_button.isEnabled = true
            store_vehicles_button.isEnabled = true

            when (it) {
                "animals" -> store_animals_button.isEnabled = false
                "items" -> store_items_button.isEnabled = false
                "people" -> store_people_button.isEnabled = false
                "buildings" -> store_buildings_button.isEnabled = false
                "food" -> store_food_button.isEnabled = false
                "vehicles" -> store_vehicles_button.isEnabled = false
            }
//            Toast.makeText(requireContext(), "Category: $it", Toast.LENGTH_SHORT).show()
        })

    activityViewModel.user.observe(viewLifecycleOwner, Observer { user ->
        user_tokens_store.text = (user.tokens.toString() + " Tokens")
})
    }
}