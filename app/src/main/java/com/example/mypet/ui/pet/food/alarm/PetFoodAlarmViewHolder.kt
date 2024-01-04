package com.example.mypet.ui.pet.food.alarm

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetRecyclerFoodRecyclerMainBinding
import com.example.mypet.domain.pet.food.PetFoodAlarmModel
import com.example.mypet.domain.toAppTime

class PetFoodAlarmViewHolder(
    private val binding: FragmentPetRecyclerFoodRecyclerMainBinding,
    private val callback: PetFoodAlarmCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var petFoodAlarmModel: PetFoodAlarmModel

    init {
        binding.root.setOnClickListener {
            callback.onClickPetFoodAlarm(petFoodAlarmModel)
        }
    }

    fun bind(petFoodAlarmModel: PetFoodAlarmModel) {
        this.petFoodAlarmModel = petFoodAlarmModel

        val iconResId = if (petFoodAlarmModel.isActive) R.drawable.baseline_notifications_24
        else R.drawable.baseline_notifications_off_24
        binding.textViewPetFoodRecyclerItemIcon.setImageResource(iconResId)
        binding.textViewPetFoodRecyclerItemTime.text =
            toAppTime(petFoodAlarmModel.hour, petFoodAlarmModel.minute)
    }
}