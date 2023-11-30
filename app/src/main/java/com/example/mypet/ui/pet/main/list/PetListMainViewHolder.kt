package com.example.mypet.ui.pet.main.list

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mypet.app.databinding.FragmentPetListRecyclerMainBinding
import com.example.mypet.domain.pet.list.PetListModel
import com.example.mypet.ui.getPetIcon

class PetListMainViewHolder(
    private val binding: FragmentPetListRecyclerMainBinding,
    private val callback: PetListCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var petListModel: PetListModel

    init {
        binding.root.setOnClickListener { callback.onPetListMainClick(petListModel) }
    }

    fun bind(petListModel: PetListModel) {
        this.petListModel = petListModel

        petListModel.avatarUri?.let {
            Glide.with(itemView)
                .load(petListModel.avatarUri)
                .circleCrop()
                .into(binding.imageViewPetListItemIcon)
        } ?: run {
            binding.imageViewPetListItemIcon.setImageResource(
                getPetIcon(petListModel.kindOrdinal, petListModel.breedOrdinal)
            )
        }
    }
}