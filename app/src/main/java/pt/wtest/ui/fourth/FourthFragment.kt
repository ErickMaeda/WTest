package pt.wtest.ui.fourth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_fourth.*
import kotlinx.android.synthetic.main.fragment_third.*
import pt.wtest.ui.third.ExerciseThreeAdapter
import pt.wtest.ui.third.FourthViewModel
import tech.innowave.wtest.BuildConfig
import tech.innowave.wtest.R

class FourthFragment : Fragment() {

    private lateinit var fourthViewModel: FourthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fourthViewModel =
            ViewModelProvider(this).get(FourthViewModel::class.java)

        return inflater.inflate(R.layout.fragment_fourth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webview.loadUrl(BuildConfig.WEB_SITE)
    }
}
