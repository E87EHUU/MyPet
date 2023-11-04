package com.example.mypet.ui.pet.detail.food

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetDetailFoodItemBinding
import com.example.mypet.domain.pet.detail.PetFoodModel

class PetDetailFoodAdapterViewHolder(
    private val binding: FragmentPetDetailFoodItemBinding,
    private val callback: PetDetailFoodAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var petFoodModel: PetFoodModel

    init {
        binding.root.setOnClickListener {
            callback.onItemClick(petFoodModel)
        }

        binding.switchPetDetailFoodItemActive.setOnClickListener {
            callback.onSwitchActive(petFoodModel)
        }
    }

    fun bind(petFoodModel: PetFoodModel) {
        this.petFoodModel = petFoodModel

        binding.textViewPetDetailFoodItemTitle.text = petFoodModel.name

        binding.textViewPetDetailFoodItemTime.text =
            toAppTime(petFoodModel.alarmHour, petFoodModel.alarmMinute)
        binding.switchPetDetailFoodItemActive.isChecked = petFoodModel.alarmIsActive ?: false

        binding.switchPetDetailFoodItemActive.isVisible = petFoodModel.alarmId != null
    }
}