package com.example.mypet.ui.pet.care.main

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerCareMainBinding
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.toAppDate

class PetCareMainViewHolder(
    private val binding: FragmentPetRecyclerCareMainBinding,
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

        binding.imageViewPetCareRecyclerMainIcon.setImageResource(petCareModel.careType.iconResId)
        binding.textViewPetCareRecyclerMainTitle.text =
            context.getString(petCareModel.careType.titleResId)

        binding.textViewPetCareRecyclerMainDate.text =
            petCareModel.nextStart?.let { toAppDate(it) } ?: "не установлено"
        petCareModel.progress?.let {
            binding.progressBarPetCareRecyclerMain.progress = petCareModel.progress
        } ?: run {
        }
    }
}