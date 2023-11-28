package com.example.mypet.ui.pet.food

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetFoodRecyclerItemBinding
import com.example.mypet.domain.pet.PetFoodAlarmModel

class PetFoodAdapterViewHolder(
    private val binding: FragmentPetFoodRecyclerItemBinding,
    private val callback: PetFoodAdapterCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var petFoodModel: PetFoodAlarmModel

    init {
        binding.root.setOnClickListener {
            callback.onItemClick(petFoodModel)
        }
    }

    fun bind(petFoodModel: PetFoodAlarmModel) {
        this.petFoodModel = petFoodModel

        val iconResId = if (petFoodModel.isActive) R.drawable.baseline_notifications_24
        else R.drawable.baseline_notifications_off_24
        binding.textViewPetFoodRecyclerItemIcon.setImageResource(iconResId)
        binding.textViewPetFoodRecyclerItemTime.text = petFoodModel.time
    }
}