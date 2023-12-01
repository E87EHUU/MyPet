package com.example.mypet.ui.pet.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mypet.app.databinding.FragmentPetFoodRecyclerMainBinding
import com.example.mypet.domain.pet.food.PetFoodAlarmModel

class PetFoodAdapter(
    private val callback: PetFoodCallback,
) : ListAdapter<PetFoodAlarmModel, PetFoodAlarmViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PetFoodAlarmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetFoodRecyclerMainBinding.inflate(inflater, parent, false)
        return PetFoodAlarmViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: PetFoodAlarmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<PetFoodAlarmModel>() {
        override fun areItemsTheSame(
            oldItem: PetFoodAlarmModel,
            newItem: PetFoodAlarmModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: PetFoodAlarmModel,
            newItem: PetFoodAlarmModel
        ) = oldItem == newItem
    }
}