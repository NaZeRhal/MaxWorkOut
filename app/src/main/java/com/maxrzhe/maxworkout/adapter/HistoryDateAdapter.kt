package com.maxrzhe.maxworkout.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maxrzhe.maxworkout.databinding.ItemHistoryRowBinding

class HistoryDateAdapter(private val context: Context, private val dates: ArrayList<String>) :
    RecyclerView.Adapter<HistoryDateAdapter.HistoryDateViewHolder>() {

    inner class HistoryDateViewHolder(val binding: ItemHistoryRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryDateViewHolder {
        val binding = ItemHistoryRowBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return HistoryDateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryDateViewHolder, position: Int) {
        with(holder.binding) {
            tvPosition.text = position.toString()
            tvItemHistory.text = dates[position]
        }
    }

    override fun getItemCount(): Int {
        return dates.size
    }
}