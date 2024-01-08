package com.example.mypet.ui.pet.care.main

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerCareAddBinding

class PetCareAddViewHolder(
    binding: FragmentPetRecyclerCareAddBinding,
    private val callback: PetCareMainCallback,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener {
            callback.onClickPetCareAdd()
        }
    }
}