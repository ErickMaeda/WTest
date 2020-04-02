package pt.wtest.ui.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_third.*
import kotlinx.coroutines.launch
import pt.wtest.R

class ThirdFragment : Fragment() {

    private lateinit var thirdViewModel: ThirdViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thirdViewModel =
            ViewModelProvider(this).get(ThirdViewModel::class.java)
        thirdViewModel.viewTypes.observe(viewLifecycleOwner, Observer{
            onLoadInputs(it)
        })
        lifecycleScope.launch {
            thirdViewModel.loadInputList()
        }
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    private fun onLoadInputs(inputs: List<ExerciseThreeAdapter.ViewTypes>) {
        rv_exercise_three.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_exercise_three.adapter = ExerciseThreeAdapter(inputs)
        rv_exercise_three.addItemDecoration(
            DividerItemDecoration(
                rv_exercise_three.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}
