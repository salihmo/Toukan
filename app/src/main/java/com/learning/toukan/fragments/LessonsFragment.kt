package com.learning.toukan.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.learning.toukan.R
import com.learning.toukan.activities.ActivityViewModel
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.fragment_lessons.*
import kotlinx.android.synthetic.main.lessons_list_item.*


class LessonsFragment : Fragment() {

    private val viewModel: LessonViewModel by viewModels()
    private val activityViewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lessons, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = LessonsAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        lessons_recyclerview.adapter = adapter
        lessons_recyclerview.layoutManager = linearLayoutManager

        viewModel.quizzes.observe(viewLifecycleOwner, Observer {
            // Display list of quizzes
            adapter.submitList(it)
        })

        activityViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            Glide
                .with(this)
                .load(user.profileImage)
                .centerCrop()
                .placeholder(R.drawable.profiledefault)
                .into(lesson_profile_picture);
            viewModel.usersStage = user.stagesCompleted

        })

    }
}



