package com.example.mypet.ui.food.alarm.service

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.ServiceFoodAlarmOverlayRecyclerItemBinding
import com.example.mypet.domain.food.alarm.FoodAlarmModel
import com.example.mypet.domain.pet.kind.getKindIconResId

class FoodAlarmServiceAdapterViewHolder(
    private val binding: ServiceFoodAlarmOverlayRecyclerItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(foodAlarmModel: FoodAlarmModel) {
        setImageAvatar(foodAlarmModel)
        binding.textViewAlarmTitle.text = foodAlarmModel.foodTitle
    }

    private fun setImageAvatar(foodAlarmModel: FoodAlarmModel) {
        println(foodAlarmModel)
        if (foodAlarmModel.petMyAvatarUri != null)
            binding.imageViewAlarmAppIcon.setImageURI(foodAlarmModel.petMyAvatarUri)
        else if (foodAlarmModel.petBreedId != null) {

        } else binding.imageViewAlarmAppIcon.setImageResource(getKindIconResId(foodAlarmModel.kindOrdinal))
    }
}