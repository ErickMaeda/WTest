package pt.wtest.ui.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_second.*
import pt.wtest.BuildConfig
import pt.wtest.R


class SecondFragment : Fragment() {

    private lateinit var secondViewModel: SecondViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        secondViewModel =
            ViewModelProvider(this).get(SecondViewModel::class.java)
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_exercise_two.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_exercise_two.adapter = ExerciseTwoAdapter()
        rv_exercise_two.addItemDecoration(
            DividerItemDecoration(
                rv_exercise_two.context,
                DividerItemDecoration.VERTICAL
            )
        )

        Picasso.get()
            .load(BuildConfig.HEADER_IMAGE)
            .placeholder(R.drawable.ic_launcher_background)
            .error(android.R.drawable.ic_menu_close_clear_cancel)
            .into(collapsing_toolbar_image_view)
    }
}
