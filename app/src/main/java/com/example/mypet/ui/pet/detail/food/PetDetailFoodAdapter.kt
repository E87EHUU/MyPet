package com.example.mypet.ui.pet.detail.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mypet.app.databinding.FragmentPetDetailFoodItemBinding
import com.example.mypet.domain.pet.detail.PetFoodModel

class PetDetailFoodAdapter(
    private val callback: PetDetailFoodAdapterCallback,
) : ListAdapter<PetFoodModel, PetDetailFoodAdapterViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PetDetailFoodAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetDetailFoodItemBinding.inflate(inflater, parent, false)
        return PetDetailFoodAdapterViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: PetDetailFoodAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<PetFoodModel>() {
        override fun areItemsTheSame(
            oldItem: PetFoodModel,
            newItem: PetFoodModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PetFoodModel,
            newItem: PetFoodModel
        ) = oldItem == newItem
    }
}