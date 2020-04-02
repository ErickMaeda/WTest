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
import pt.wtest.utils.gone
import pt.wtest.utils.hideKeyboard
import pt.wtest.utils.show


class FirstFragment : Fragment() {

    private lateinit var viewModel: FirstViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(FirstViewModel::class.java)

        // Observe changes from postalCodes list
        viewModel.postalCodes.observeForever { onLoadPostalCodes(it) }

        // Check for loading state
        viewModel.loadingState.observeForever { onLoadingStateChange(it) }

        lifecycleScope.launch { viewModel.initialize() }

        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_search.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                (et_search).hideKeyboard()
            }
        }
        // Detect changes on search
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d(javaClass.simpleName, "onTextChanged $p0")
                if (p0?.length!! >= 3) {
                    lifecycleScope.launch { viewModel.loadPostalCodes(p0.toString()) }
                } else {
                    onLoadPostalCodes(emptyList())
                    tv_state.show()
                    tv_state.text = getString(R.string.exercise_one_text_state_loading_initial)
                }
            }
        })
    }

    private fun onLoadingStateChange(value: FirstViewModel.LoadingState) {
        if (value == FirstViewModel.LoadingState.NORMAL) {
            pb_exercise_one.gone()
            tv_state.gone()
        } else if (
            value == FirstViewModel.LoadingState.INITIAL ||
            value == FirstViewModel.LoadingState.FETCHING ||
            value == FirstViewModel.LoadingState.DOWNLOADING
        ) {
            pb_exercise_one.show()
            when (value) {
                FirstViewModel.LoadingState.INITIAL -> {
                    tv_state.text = getString(R.string.exercise_one_text_state_loading_checking)
                    tv_state.show()
                }
                FirstViewModel.LoadingState.DOWNLOADING -> {
                    tv_state.text = getString(R.string.exercise_one_text_state_loading_downloading)
                    tv_state.show()
                }
                else -> tv_state.gone()
            }
        }
    }

    private fun onLoadPostalCodes(list: List<PostalCodeEntity>) {
        Log.d(javaClass.simpleName, "onLoadPostalCodes ${list.size}")

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
