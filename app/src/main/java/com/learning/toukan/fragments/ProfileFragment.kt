package com.learning.toukan.fragments

import android.graphics.ColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.Navigation.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.learning.toukan.R
import com.learning.toukan.activities.ActivityViewModel
import kotlinx.android.synthetic.main.fragment_board.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.settings_fragment.*

class ProfileFragment : Fragment() {

    private val activityViewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val colour = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
//        val filter = SimpleColorFilter(colour)
//        val keyPath = KeyPath("**")
//        val callback: LottieValueCallback<ColorFilter> = LottieValueCallback(filter)
//        animationView.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback)

        activityViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            Glide
                .with(this)
                .load(user.profileImage)
                .centerCrop()
                .placeholder(R.drawable.profiledefault)
                .into(profile_picture);

            profile_name.text = user.name
            profile_stages_number.text = user.stagesCompleted.toString()
            profile_stickers_number.text = user.stickersAmount.toString()
            profile_tokens_number.text = user.tokens.toString()

            if (user.stagesCompleted == 1){
                profile_stages_word.setText("Stage completed")
            }
            if (user.tokens == 1){
                profile_tokens_word.setText("Token")
            }
            if(user.stickersAmount == 1){
                profile_stickers_word.setText("Sticker")
            }

            if (profile_tokens_number.text.length == 4) {
                profile_tokens_number.textSize = 30f
            } else if (profile_tokens_number.text.length > 4) {
                profile_tokens_number.textSize = 25f
            } else profile_tokens_number.textSize = 40f


            if (profile_stages_number.text.length >= 3) {
                profile_stages_number.textSize = 30f
            } else profile_stages_number.textSize = 40f

            if (profile_stickers_number.text.length >= 3) {
                profile_stickers_number.textSize = 30f
            } else profile_stickers_number.textSize = 40f

        })
        settings_button.setOnClickListener {
           val directions = ProfileFragmentDirections.actionNavigationProfileToSettingsFragment()
            findNavController().navigate(directions)
            settings_button.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.rotation))
        }
    }
    fun onBackPressed() {
        if (requireActivity().supportFragmentManager.backStackEntryCount > 0) {
            requireActivity().supportFragmentManager.popBackStack()
        } else {
           onBackPressed()
        }
    }
    }