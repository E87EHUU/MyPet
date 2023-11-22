package com.example.mypet.ui.alarm.repeat.service

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.ServiceFoodAlarmOverlayRecyclerItemBinding
import com.example.mypet.domain.food.alarm.FoodAlarmModel
import com.example.mypet.ui.getPetIcon

class FoodAlarmServiceAdapterViewHolder(
    private val binding: ServiceFoodAlarmOverlayRecyclerItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(foodAlarmModel: FoodAlarmModel) {
        if (foodAlarmModel.petMyAvatarUri != null)
            binding.imageViewAlarmAppIcon.setImageURI(foodAlarmModel.petMyAvatarUri)
        else
            binding.imageViewAlarmAppIcon.setImageResource(
                getPetIcon(foodAlarmModel.petKindOrdinal, foodAlarmModel.petBreedOrdinal)
            )
        binding.textViewAlarmTitle.text = foodAlarmModel.foodTitle
    }
}