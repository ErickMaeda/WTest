package pt.wtest.ui.first

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.launch
import pt.wtest.R
import pt.wtest.data.entities.PostalCodeEntity

class FirstFragment : Fragment() {

    private lateinit var viewModel: FirstViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(FirstViewModel::class.java)
        viewModel.postalCodes.observeForever {
            onLoadPostalCodes(it)
        }

        lifecycleScope.launch { viewModel.initialize() }

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d(javaClass.simpleName, "onTextChanged $p0")
                lifecycleScope.launch { viewModel.loadPostalCodes(p0.toString()) }
            }
        })
    }

    private fun onLoadPostalCodes(list: List<PostalCodeEntity>) {
        Log.d(javaClass.simpleName, "On load postal codes ${list.size}")

        rv_exercise_one.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_exercise_one.adapter = ExerciseOneAdapter(list)
        rv_exercise_one.addItemDecoration(
            DividerItemDecoration(
                rv_exercise_one.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}
