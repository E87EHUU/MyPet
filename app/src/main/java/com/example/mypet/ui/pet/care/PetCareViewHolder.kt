package com.example.mypet.ui.pet.care

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerCareBinding
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.ui.pet.care.main.PetCareMainAdapter
import com.example.mypet.ui.pet.care.main.PetCareMainCallback

class PetCareViewHolder(
    binding: FragmentPetRecyclerCareBinding,
    callback: PetCareMainCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private var petCareModels: List<PetCareModel>? = null

    private val petCareAdapter = PetCareMainAdapter(callback)

    init {
        binding.root.itemAnimator = null
        binding.root.adapter = petCareAdapter
    }

    fun bind(petCareModels: List<PetCareModel>?) {
        this.petCareModels = petCareModels
        petCareAdapter.submitList(petCareModels)
    }
}