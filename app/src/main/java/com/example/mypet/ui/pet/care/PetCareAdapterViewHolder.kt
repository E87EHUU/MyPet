package com.example.mypet.ui.pet.care

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetCareRecyclerItemBinding
import com.example.mypet.domain.pet.care.PetCareModel

class PetCareAdapterViewHolder(
    private val binding: FragmentPetCareRecyclerItemBinding,
    private val callback: PetCareAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var petCareModel: PetCareModel

    init {
        binding.root.setOnClickListener {
            callback.onItemClick(petCareModel)
        }
    }

    fun bind(petCareModel: PetCareModel) {
        this.petCareModel = petCareModel

        binding.imageViewPetCareRecyclerItemIcon.setImageResource(petCareModel.iconResId)
        binding.textViewPetCareRecyclerItemTitle.text = petCareModel.title
        binding.textViewPetCareRecyclerItemDate.text = petCareModel.date
        binding.progressBarPetCareRecyclerItem.progress = petCareModel.progress
    }
}