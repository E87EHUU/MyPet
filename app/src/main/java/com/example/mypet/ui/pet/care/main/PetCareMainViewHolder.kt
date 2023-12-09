package com.example.mypet.ui.pet.care.main

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetCareRecyclerItemBinding
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.toAppDate

class PetCareMainViewHolder(
    private val binding: FragmentPetCareRecyclerItemBinding,
    private val callback: PetCareMainCallback,
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

        binding.textViewPetCareRecyclerItemDate.text =
            petCareModel.nextStart?.let { toAppDate(it) } ?: "не установлено"
        petCareModel.progress?.let {
            binding.progressBarPetCareRecyclerItem.progress = petCareModel.progress
            binding.progressBarPetCareRecyclerItem.isVisible = true
        } ?: run {
            binding.progressBarPetCareRecyclerItem.isVisible = false
        }
    }
}