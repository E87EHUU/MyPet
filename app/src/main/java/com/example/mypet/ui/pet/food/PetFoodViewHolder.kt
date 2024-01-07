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
    private val petFoodAdapter = PetFoodAlarmAdapter(callback)

    private var petFoodModel: PetFoodModel? = null

    init {
        binding.recyclerViewPetRecyclerFood.itemAnimator = null
        binding.recyclerViewPetRecyclerFood.adapter = petFoodAdapter
        binding.root.setOnClickListener { petFoodModel?.let { callback.onClickPetFood(it.care) } }
    }

    fun bind(petFoodModel: PetFoodModel?) {
        this.petFoodModel = petFoodModel
        petFoodAdapter.submitList(petFoodModel?.alarms)

        petFoodModel?.let {
            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
    }
}