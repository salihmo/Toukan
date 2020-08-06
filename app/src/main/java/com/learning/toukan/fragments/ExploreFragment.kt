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
import com.learning.toukan.R
import com.learning.toukan.activities.ActivityViewModel
import kotlinx.android.synthetic.main.fragment_explore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExploreFragment : Fragment() {

    private val viewModel: ExploreViewModel by viewModels()
    private val activityViewModel: ActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        quiz1.isEnabled = false
//
//        if (Quiz().stage + 1 <= User().stagesCompleted){
//            quiz1.isEnabled = true
//        }
//
//
//        fun launchQuiz(id: String) {
//            val intent = Intent(activity, SecondActivity::class.java)
//            intent.putExtra("quiz_id", id)
//            startActivity(intent)
//        }
//
//        view.findViewById<View>(R.id.quiz1).setOnClickListener {
//            launchQuiz("tgMYoF6Ye3RfkeQ0yTIf")
//        }
//        view.findViewById<View>(R.id.quiz2).setOnClickListener {
//            launchQuiz("P4z5QTQCOQpfxbCuHZkj")
//        }

//            if (MainActivity().userStage > MainActivity().quizStage)
//                quiz1.isEnabled

        val adapter = QuizzesAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        quizzes_list_recyclerview.adapter = adapter
        quizzes_list_recyclerview.layoutManager = linearLayoutManager

        viewModel.quizzes.observe(viewLifecycleOwner, Observer {
            // Display list of quizzes
            adapter.submitList(it)
        })

        activityViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            viewModel.usersStage = user.stagesCompleted
        })
    }
}





