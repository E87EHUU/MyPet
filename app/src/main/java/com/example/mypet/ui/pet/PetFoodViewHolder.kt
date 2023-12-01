package com.example.mypet.ui.pet

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerFoodBinding
import com.example.mypet.domain.pet.food.PetFoodModel
import com.example.mypet.ui.pet.food.PetFoodAdapter
import com.example.mypet.ui.pet.food.PetFoodCallback

class PetFoodViewHolder(
    private val binding: FragmentPetRecyclerFoodBinding,
    private val callback: PetFoodCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private lateinit var petFoodModel: PetFoodModel
    private val petFoodAdapter = PetFoodAdapter(callback)

    init {
        binding.recyclerViewPetFoodList.adapter = petFoodAdapter
        binding.root.setOnClickListener { callback.onPetFoodClick(petFoodModel.care) }
    }

    fun bind(petFoodModel: PetFoodModel?) {
        petFoodModel?.let {
            this.petFoodModel = petFoodModel
        }

        petFoodAdapter.submitList(petFoodModel?.alarmModels)
    }
}