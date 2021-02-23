package com.maxrzhe.maxworkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maxrzhe.maxworkout.ExerciseModel
import com.maxrzhe.maxworkout.R
import com.maxrzhe.maxworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(private val exercises: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ExerciseViewHolder>() {


    inner class ExerciseViewHolder(val binding: ItemExerciseStatusBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding =
            ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        with(holder.binding.tvItemExercise) {
            val model: ExerciseModel = exercises[position]
            text = model.getId().toString()
            when {
                model.getIsSelected() -> {
                    background = ContextCompat.getDrawable(
                        context,
                        R.drawable.item_circular_thin_color_accent_border
                    )
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                }
                model.getIsCompleted() -> {
                    background = ContextCompat.getDrawable(
                        context,
                        R.drawable.item_circular_color_accent_background
                    )
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                else -> {
                    background = ContextCompat.getDrawable(
                        context,
                        R.drawable.item_circular_color_gray_background
                    )
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return exercises.size
    }
}