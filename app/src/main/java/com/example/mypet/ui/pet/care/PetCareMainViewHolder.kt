package com.example.mypet.ui.pet.care

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetCareRecyclerItemBinding
import com.example.mypet.domain.pet.care.PetCareModel

class PetCareMainViewHolder(
    private val binding: FragmentPetCareRecyclerItemBinding,
    private val callback: PetCareCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private lateinit var petCareModel: PetCareModel

    init {
        binding.root.setOnClickListener {
            callback.onPetCareClick(petCareModel)
        }
    }

    fun bind(petCareModel: PetCareModel) {
        this.petCareModel = petCareModel

        binding.imageViewPetCareRecyclerItemIcon.setImageResource(petCareModel.careType.iconResId)
        binding.textViewPetCareRecyclerItemTitle.text =
            context.getString(petCareModel.careType.titleResId)

        binding.textViewPetCareRecyclerItemDate.text = petCareModel.date ?: "не установлено"
        petCareModel.progress?.let {
            binding.progressBarPetCareRecyclerItem.progress = petCareModel.progress
            binding.progressBarPetCareRecyclerItem.isVisible = true
        } ?: run {
            binding.progressBarPetCareRecyclerItem.isVisible = false
        }
    }
}