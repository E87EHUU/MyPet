package com.example.mypet.ui.pet

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerFoodBinding
import com.example.mypet.domain.pet.food.PetFoodAlarmModel

import com.example.mypet.ui.pet.food.PetFoodAdapter
import com.example.mypet.ui.pet.food.PetFoodCallback

class PetFoodViewHolder(
    private val binding: FragmentPetRecyclerFoodBinding,
    private val callback: PetFoodCallback,
) : RecyclerView.ViewHolder(binding.root), PetFoodCallback {
    private val context = binding.root.context
    private lateinit var petFoodModel: PetFoodAlarmModel
    private val petFoodAdapter = PetFoodAdapter(this)

    fun bind(petFoodModel: List<PetFoodAlarmModel>) {
        //this.petFoodModel = petFoodModel
        binding.recyclerViewPetFoodList.adapter = petFoodAdapter
        petFoodAdapter.submitList(petFoodModel)


    }

    override fun onPetFoodAlarmClick(petFoodAlarmModel: PetFoodAlarmModel) {
        TODO("Not yet implemented")
    }
}