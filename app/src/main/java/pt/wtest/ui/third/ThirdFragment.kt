package pt.wtest.ui.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_third.*
import tech.innowave.wtest.R

class ThirdFragment : Fragment() {

    private lateinit var thirdViewModel: ThirdViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thirdViewModel =
            ViewModelProvider(this).get(ThirdViewModel::class.java)

        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listExerciseThree: MutableList<ExerciseThreeAdapter.ViewTypes> = mutableListOf()
        var nextViewType = ExerciseThreeAdapter.ViewTypes.NORMAL
        for (i in 1 .. 50) {
            listExerciseThree.add(nextViewType)
            if (nextViewType == ExerciseThreeAdapter.ViewTypes.ALL_UPPER_CASE) {
                nextViewType = ExerciseThreeAdapter.ViewTypes.NORMAL
            } else if (nextViewType == ExerciseThreeAdapter.ViewTypes.NUMBER){
                nextViewType = ExerciseThreeAdapter.ViewTypes.ALL_UPPER_CASE
            } else {
                nextViewType = ExerciseThreeAdapter.ViewTypes.NUMBER
            }
        }
        rv_exercise_three.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_exercise_three.adapter = ExerciseThreeAdapter(listExerciseThree)
    }
}
