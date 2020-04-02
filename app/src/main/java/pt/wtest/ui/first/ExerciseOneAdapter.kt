package pt.wtest.ui.first

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_exercise_three_text_normal.view.*
import pt.wtest.R
import pt.wtest.data.entities.PostalCodeEntity

class ExerciseOneAdapter(private val list: List<PostalCodeEntity>) :
    RecyclerView.Adapter<ExerciseOneAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_exercise_one, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.apply {
            tv_label.text = "${list[position].num_cod_postal}-${list[position].ext_cod_postal}, ${list[position].nome_localidade}"
        }
    }
}