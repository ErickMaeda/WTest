package pt.wtest.ui.second

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_exercise_three_text_normal.view.*
import pt.wtest.R

class ExerciseTwoAdapter() :
    RecyclerView.Adapter<ExerciseTwoAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int) = 50

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_exercise_two, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = 50

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.apply {
            tv_label.text = "Item ${position + 1}"
        }
    }
}