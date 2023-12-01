package com.example.mypet.ui.pet

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerCareBinding
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.ui.pet.care.PetCareAdapter
import com.example.mypet.ui.pet.care.PetCareCallback

class PetCareViewHolder(
    private val binding: FragmentPetRecyclerCareBinding,
    private val callback: PetCareCallback,
) : RecyclerView.ViewHolder(binding.root), PetCareCallback {
    private val context = binding.root.context
    private lateinit var petCareModels: List<PetCareModel>

    private val petCareAdapter = PetCareAdapter(this)

    init {
        binding.recyclerViewPetCareList.adapter = petCareAdapter
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