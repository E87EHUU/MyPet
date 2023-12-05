package com.example.mypet.ui.pet.main.list

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerMainRecyclerAddBinding

class PetListAddViewHolder(
    private val binding: FragmentPetRecyclerMainRecyclerAddBinding,
    private val callback: PetListCallback,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener { callback.onClickPetAdd() }
    }
}