package com.example.mypet.ui.pet

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mypet.app.databinding.FragmentPetRecyclerMainBinding
import com.example.mypet.domain.pet.PetModel
import com.example.mypet.ui.getPetIcon
import com.example.mypet.ui.getPetName
import com.example.mypet.ui.pet.main.PetMainCallback
import com.example.mypet.ui.pet.main.list.PetListAdapter
import com.example.mypet.ui.pet.main.list.PetListCallback

class PetMainViewHolder(
    private val binding: FragmentPetRecyclerMainBinding,
    private val callback: PetMainCallback,
) : RecyclerView.ViewHolder(binding.root), PetListCallback {
    private val context = binding.root.context
    private lateinit var pet: List<PetModel>
    private val petListAdapter: PetListAdapter = PetListAdapter(this)

    init {
        binding.recyclerViewPetList.adapter = petListAdapter
    }

    fun bind(pet: List<PetModel>) {
        this.pet = pet
        petListAdapter.submitList(pet)

        if (pet.isNotEmpty()) {
            updatePet(pet.first())
            onPetListMainClick(pet.first())
        }
    }

    private fun updatePet(petListModel: PetModel) {
        with(petListModel) {
            if (avatarUri != null) {
                Glide.with(context)
                    .load(avatarUri)
                    .circleCrop()
                    .into(binding.imageViewPetAvatarIcon)
            } else
                binding.imageViewPetAvatarIcon
                    .setImageResource(getPetIcon(kindOrdinal, breedOrdinal))

            binding.textViewPetName.text = name
            binding.textViewPetBreedName.text =
                context.getString(getPetName(kindOrdinal, breedOrdinal))

            age?.let {
                binding.textViewPetAgeText.text = age
                binding.materialCardViewPetAge.isVisible = true
            } ?: run {
                binding.materialCardViewPetAge.isVisible = false
            }

            weight?.let {
                binding.textViewPetWeightText.text = weight
                binding.materialCardViewPetWeight.isVisible = true
            } ?: run {
                binding.materialCardViewPetWeight.isVisible = false
            }
        }
    }

    override fun onPetListAddClick() {
        callback.onPetAddClick()
    }

    override fun onPetListMainClick(petListModel: PetModel) {
        callback.onPetClick(petListModel)
    }
}