package com.example.mypet.ui.pet.food

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetFoodRecyclerItemBinding
import com.example.mypet.domain.pet.food.PetFoodModel

class PetFoodAdapterViewHolder(
    private val binding: FragmentPetFoodRecyclerItemBinding,
    private val callback: PetFoodAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var petFoodModel: PetFoodModel

    init {
        binding.root.setOnClickListener {
            callback.onItemClick(petFoodModel)
        }
    }

    fun bind(petFoodModel: PetFoodModel) {
        this.petFoodModel = petFoodModel

        val iconResId = if (petFoodModel.isActive) R.drawable.baseline_notifications_24
        else R.drawable.baseline_notifications_off_24
        binding.textViewPetFoodRecyclerItemIcon.setImageResource(iconResId)
        binding.textViewPetFoodRecyclerItemTitle.text = petFoodModel.title
        binding.textViewPetFoodRecyclerItemTime.text = petFoodModel.time
    }
}