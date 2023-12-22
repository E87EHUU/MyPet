package com.example.mypet.ui.pet.main.list

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mypet.app.databinding.FragmentPetRecyclerMainRecyclerMainBinding
import com.example.mypet.domain.pet.list.PetListModel
import com.example.mypet.ui.getPetIcon

class PetListMainViewHolder(
    private val binding: FragmentPetRecyclerMainRecyclerMainBinding,
    private val callback: PetListCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var petListModel: PetListModel

    init {
        binding.root.setOnClickListener { callback.onClickPet(petListModel) }
    }

    fun bind(petListModel: PetListModel) {
        this.petListModel = petListModel

        Glide.with(itemView)
            .load(petListModel.avatarUri)
            .circleCrop()
            .placeholder(getPetIcon(petListModel.kindOrdinal, petListModel.breedOrdinal))
            .into(binding.imageViewPetRecyclerMainRecyclerMainAvatarIcon)

        binding.textViewPetRecyclerMainRecyclerMainName.text = petListModel.name
    }
}