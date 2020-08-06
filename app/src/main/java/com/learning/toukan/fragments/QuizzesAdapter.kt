package com.learning.toukan.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.learning.toukan.R
import com.learning.toukan.activities.QuizActivity
import com.learning.toukan.models.Quiz
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.quizzes_list_item.view.*

class QuizzesAdapter : ListAdapter<Pair<Quiz, Boolean>, QuizzesAdapter.QuizViewHolder>(QuizzesDiffCallback()) {

    class QuizViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Pair<Quiz, Boolean>) = with(itemView) {
            val quiz = item.first
            val enabled = item.second
            quizzes_list_item_title.text = quiz.title
            quizzes_list_item_button.isEnabled = enabled
            quizzes_list_item_button.setOnClickListener {
                val intent = Intent(context, QuizActivity::class.java)
                intent.putExtra("quiz", quiz)
                context.startActivity(intent)
            }
        }

        companion object {
            fun from(parent: ViewGroup) : QuizViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.quizzes_list_item, parent, false)
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

class QuizzesDiffCallback : DiffUtil.ItemCallback<Pair<Quiz, Boolean>>() {
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