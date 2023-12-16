package com.example.mypet.ui.pet.care

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerCareBinding
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.ui.pet.care.main.PetCareMainAdapter
import com.example.mypet.ui.pet.care.main.PetCareMainCallback

class PetCareViewHolder(
    private val binding: FragmentPetRecyclerCareBinding,
    private val callback: PetCareMainCallback,
) : RecyclerView.ViewHolder(binding.root), PetCareMainCallback {
    private val context = binding.root.context
    private lateinit var petCareModels: List<PetCareModel>

    private val petCareAdapter = PetCareMainAdapter(this)

    init {
        binding.root.itemAnimator = null
        binding.root.adapter = petCareAdapter
    }

    fun bind(petCareModels: List<PetCareModel>?) {
        petCareModels?.let {
            this.petCareModels = petCareModels
            petCareAdapter.submitList(petCareModels)

            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
    }

    override fun onPetCareClick(petCareModel: PetCareModel) {
        callback.onPetCareClick(petCareModel)
    }
}