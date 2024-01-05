package com.example.mypet.ui.pet.main.list

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
            .load(petListMainModel.avatarUri)
            .circleCrop()
            .placeholder(getPetIcon(petListMainModel.kindOrdinal, petListMainModel.breedOrdinal))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageViewPetRecyclerMainRecyclerMainAvatarIcon)

        binding.textViewPetRecyclerMainRecyclerMainName.text = petListMainModel.name
    }
}