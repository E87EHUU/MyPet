package com.example.mypet.ui.care

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerFoodBinding
import com.example.mypet.domain.care.CareViewHolderFoodModel

class FoodAdapterAboutViewHolder(
    private val binding: FragmentCareRecyclerFoodBinding,
    private val callback: CareFoodCallback,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(foodAboutModel: CareViewHolderFoodModel) {

    }
}