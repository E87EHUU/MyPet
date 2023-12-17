package com.example.mypet.ui.pet.food.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mypet.app.databinding.FragmentPetRecyclerFoodRecyclerMainBinding
import com.example.mypet.domain.alarm.AlarmMinModel

class PetFoodAlarmAdapter(
    private val callback: PetFoodAlarmCallback,
) : ListAdapter<AlarmMinModel, PetFoodAlarmViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PetFoodAlarmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetRecyclerFoodRecyclerMainBinding.inflate(inflater, parent, false)
        return PetFoodAlarmViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: PetFoodAlarmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<AlarmMinModel>() {
        override fun areItemsTheSame(
            oldItem: AlarmMinModel,
            newItem: AlarmMinModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: AlarmMinModel,
            newItem: AlarmMinModel
        ) = oldItem == newItem
    }
}