package com.example.mypet.ui.food

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentFoodRecyclerItemBinding
import com.example.mypet.domain.food.FoodModel

class FoodAdapterViewHolder(
    private val binding: FragmentFoodRecyclerItemBinding,
    private val callback: FoodAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var foodModel: FoodModel

    init {
        binding.root.setOnClickListener {
            callback.onItemClick(foodModel)
        }

        binding.switchFoodItemActive.setOnClickListener {
            callback.onSwitchActive(foodModel)
        }
    }

    fun bind(foodModel: FoodModel) {
        this.foodModel = foodModel

        with(foodModel) {
            binding.textViewFoodItemText.text = foodTitle
            binding.textViewFoodItemTime.text = toAppTime(alarmHour, alarmMinute)

            binding.switchFoodItemActive.isVisible = alarmId != null
            binding.switchFoodItemActive.isChecked = alarmIsActive ?: false
        }
    }
}