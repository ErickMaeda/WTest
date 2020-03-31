package pt.wtest.ui.third

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_exercise_one_text_normal.view.*
import pt.wtest.R
import pt.wtest.utils.hideKeyboard

class ExerciseThreeAdapter(private val list: List<ViewTypes>) :
    RecyclerView.Adapter<ExerciseThreeAdapter.MyViewHolder>() {

    enum class ViewTypes {
        NORMAL, NUMBER, ALL_UPPER_CASE
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int {
        return list[position].ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutId = when (viewType) {
            ViewTypes.ALL_UPPER_CASE.ordinal -> R.layout.adapter_exercise_one_text_uppercase
            ViewTypes.NUMBER.ordinal -> R.layout.adapter_exercise_one_number
            else -> R.layout.adapter_exercise_one_text_normal
        }
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = 50

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.apply {
            val itemViewTypeDesc = ViewTypes.values()[getItemViewType(position)].name
            tv_label.text = "$itemViewTypeDesc (${position + 1})"
            et_input.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard()
                }
            }
        }
    }
}