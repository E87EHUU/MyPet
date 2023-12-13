package com.example.mypet.ui.pet.food

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerFoodBinding
import com.example.mypet.domain.pet.food.PetFoodModel
import com.example.mypet.ui.pet.food.alarm.PetFoodAlarmAdapter
import com.example.mypet.ui.pet.food.alarm.PetFoodAlarmCallback

class PetFoodViewHolder(
    private val binding: FragmentPetRecyclerFoodBinding,
    private val callback: PetFoodAlarmCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private lateinit var petFoodModel: PetFoodModel
    private val petFoodAdapter = PetFoodAlarmAdapter(callback)

    init {
        binding.recyclerViewPetRecyclerFood.itemAnimator = null
        binding.recyclerViewPetRecyclerFood.adapter = petFoodAdapter
        binding.root.setOnClickListener { callback.onClickPetFood(petFoodModel.care) }
    }

    fun bind(petFoodModel: PetFoodModel?) {
        petFoodModel?.let {
            this.petFoodModel = petFoodModel
            petFoodAdapter.submitList(petFoodModel.alarmModels)
            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
    }
}