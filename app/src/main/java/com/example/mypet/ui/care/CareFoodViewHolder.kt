package com.example.mypet.ui.care

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerFoodBinding
import com.example.mypet.domain.care.CareViewHolderFoodModel

class CareFoodViewHolder(
    private val binding: FragmentCareRecyclerFoodBinding,
    private val callback: CareFoodCallback,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            callback.onItemClick()
        }
    }

    fun bind(careFoodModel: CareViewHolderFoodModel) {
    }
}