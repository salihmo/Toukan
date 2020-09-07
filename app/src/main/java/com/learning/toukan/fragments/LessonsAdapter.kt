package com.learning.toukan.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.toukan.R
import com.learning.toukan.activities.LessonActivity
import com.learning.toukan.activities.QuizActivity
import com.learning.toukan.models.Quiz
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.lessons_list_item.view.*
import kotlinx.android.synthetic.main.quizzes_list_item.view.*

class LessonsAdapter : ListAdapter<Pair<Quiz, Boolean>, LessonsAdapter.QuizViewHolder>(LessonsDiffCallback()) {

    class QuizViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Pair<Quiz, Boolean>) = with(itemView) {

            val quiz = item.first
            Glide
                .with(this)
                .load(quiz.stageImage)
                .placeholder(R.drawable.revive)
                .into(lesson_image)
            val enabled = item.second
            lesson_title.text = quiz.title
            lesson_start_button.isEnabled = enabled
            lesson_start_button.setOnClickListener {
                val intent = Intent(context, LessonActivity::class.java)
                intent.putExtra("quiz", quiz)
                context.startActivity(intent)
            }
        }
        companion object {
            fun from(parent: ViewGroup) : QuizViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.lessons_list_item, parent, false)
                return QuizViewHolder(view)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        return QuizViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class LessonsDiffCallback : DiffUtil.ItemCallback<Pair<Quiz, Boolean>>() {
    override fun areItemsTheSame(
        oldItem: Pair<Quiz, Boolean>,
        newItem: Pair<Quiz, Boolean>
    ): Boolean {
        return oldItem.first.id == newItem.first.id
    }
    override fun areContentsTheSame(
        oldItem: Pair<Quiz, Boolean>,
        newItem: Pair<Quiz, Boolean>
    ): Boolean {
        return oldItem == newItem
    }
}