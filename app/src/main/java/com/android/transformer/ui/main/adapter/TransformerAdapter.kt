package com.android.transformer.ui.main.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.android.transformer.R
import com.android.transformer.model.Transformer
import com.android.transformer.util.Utility
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.transformer_list_item.view.*

/**
 * Adaapter class for the transformer listing
 */
class TransformerAdapter(
    var list: ArrayList<Transformer>,
    var listener: onItemClicked
) :
    RecyclerView.Adapter<TransformerAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.findViewById(R.id.iv_team) as ImageView
            itemView.findViewById(R.id.tv_team_data) as TextView
            itemView.findViewById(R.id.btn_edit) as ImageButton
            itemView.findViewById(R.id.btn_delete) as ImageButton
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.transformer_list_item, parent,
            false
        )
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        Picasso.get().load(list.get(position).team_icon).into(holder.itemView.iv_team)
        var data = ""
        list.get(position).let {
            data =
                "Name: ${it.name}    Strength: ${it.strength} \nIntelligence: ${it.intelligence}    Speed: ${it.speed} \nEndurance: ${it.endurance}    Rank: ${it.rank} \nCourage: ${it.courage}    Firepower: ${it.firepower} \nSkill: ${it.skill}    "
            when (it.team) {
                "A" -> data = data + "Team: Autobot"
                "D" -> data = data + "Team: Decepticon"
            }
            data = data + "\nOverall Rating: ${Utility.getOverallRating(it)}"
        }
        holder.itemView.tv_team_data.setText(data)
        holder.itemView.btn_edit.setOnClickListener {
            listener.onEditClicked(position, list.get(position))
        }

        holder.itemView.btn_delete.setOnClickListener {
            listener.onDeleteClicked(position, list.get(position))
        }
    }

    fun updateList(transformerList: Transformer) {
        list.remove(transformerList)
        notifyDataSetChanged()
    }

    interface onItemClicked {
        fun onEditClicked(position: Int, transformer: Transformer)
        fun onDeleteClicked(position: Int, transformer: Transformer)
    }
}