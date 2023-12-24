package com.example.mypet.ui.pet.main.list

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetRecyclerMainRecyclerMainBinding
import com.example.mypet.domain.pet.list.PetListMainModel
import com.example.mypet.ui.getPetIcon

class PetListMainViewHolder(
    private val binding: FragmentPetRecyclerMainRecyclerMainBinding,
    private val callback: PetListCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var petListMainModel: PetListMainModel

    init {
        binding.root.setOnClickListener { callback.onClickPet(petListMainModel) }
    }

    fun bind(petListMainModel: PetListMainModel) {
        this.petListMainModel = petListMainModel

        with(binding) {
            if (this@PetListMainViewHolder.petListMainModel.isActive)
                frameLayoutPetRecyclerMainRecyclerMainAvatar
                    .setBackgroundResource(R.drawable.pet_active_avatar_round_background)
            else
                frameLayoutPetRecyclerMainRecyclerMainAvatar
                    .setBackgroundResource(R.drawable.pet_avatar_round_background)
        }

        Glide.with(itemView)
            .load(this.petListMainModel.avatarUri)
            .circleCrop()
            .placeholder(getPetIcon(this.petListMainModel.kindOrdinal, this.petListMainModel.breedOrdinal))
            .into(binding.imageViewPetRecyclerMainRecyclerMainAvatarIcon)

        binding.textViewPetRecyclerMainRecyclerMainName.text = this.petListMainModel.name
    }
}