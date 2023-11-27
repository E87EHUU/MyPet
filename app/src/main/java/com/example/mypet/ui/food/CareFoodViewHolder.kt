package com.example.mypet.ui.food

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerFoodBinding
import com.example.mypet.domain.food.CareFoodModel

class CareFoodViewHolder(
    private val binding: FragmentCareRecyclerFoodBinding,
    private val callback: CareFoodCallback,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            callback.onItemClick()
        }
    }

    fun bind(careFoodModel: CareFoodModel) {
    }
}