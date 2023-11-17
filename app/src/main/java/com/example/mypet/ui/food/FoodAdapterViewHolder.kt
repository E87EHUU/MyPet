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

        binding.switchPetDetailFoodItemActive.setOnClickListener {
            callback.onSwitchActive(foodModel)
        }
    }

    fun bind(foodModel: FoodModel) {
        this.foodModel = foodModel

        binding.textViewPetDetailFoodItemTitle.text = foodModel.foodTitle

        binding.textViewPetDetailFoodItemTime.text =
            toAppTime(foodModel.alarmHour, foodModel.alarmMinute)
        binding.switchPetDetailFoodItemActive.isChecked = foodModel.alarmIsActive ?: false

        binding.switchPetDetailFoodItemActive.isVisible = foodModel.alarmId != null
    }
}