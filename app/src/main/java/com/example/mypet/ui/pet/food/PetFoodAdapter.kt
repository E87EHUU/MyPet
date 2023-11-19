package com.example.mypet.ui.pet.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mypet.app.databinding.FragmentPetFoodRecyclerItemBinding
import com.example.mypet.domain.pet.food.PetFoodModel

class PetFoodAdapter(
    private val callback: PetFoodAdapterCallback,
) : ListAdapter<PetFoodModel, PetFoodAdapterViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PetFoodAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetFoodRecyclerItemBinding.inflate(inflater, parent, false)
        return PetFoodAdapterViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: PetFoodAdapterViewHolder, position: Int) {
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