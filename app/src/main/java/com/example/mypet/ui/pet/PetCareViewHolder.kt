package com.example.mypet.ui.pet

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
    private lateinit var petCareModel: PetCareModel

    private val petCareAdapter = PetCareAdapter(this)

    fun bind(petCareModel: List<PetCareModel>) {
//        this.petCareModel = petCareModel

        binding.recyclerViewPetCareList.adapter = petCareAdapter
        petCareAdapter.submitList(petCareModel)


    }

    override fun onPetCareClick(petCareModel: com.example.mypet.domain.pet.care.PetCareModel) {
        TODO("Not yet implemented")
    }
}