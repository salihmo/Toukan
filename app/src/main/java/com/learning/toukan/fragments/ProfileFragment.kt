package com.learning.toukan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.learning.toukan.R
import com.learning.toukan.activities.ActivityViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

        })
        settings_button.setOnClickListener {
           val directions = ProfileFragmentDirections.actionNavigationProfileToSettingsFragment()
            findNavController().navigate(directions)
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










