package com.example.mypet.ui.alarm.repeat.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mypet.app.databinding.ServiceFoodAlarmOverlayRecyclerItemBinding
import com.example.mypet.domain.food.alarm.FoodAlarmModel

class FoodAlarmServiceAdapter(
) : ListAdapter<FoodAlarmModel, FoodAlarmServiceAdapterViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodAlarmServiceAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ServiceFoodAlarmOverlayRecyclerItemBinding.inflate(inflater, parent, false)
        return FoodAlarmServiceAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodAlarmServiceAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<FoodAlarmModel>() {
        override fun areItemsTheSame(
            oldItem: FoodAlarmModel,
            newItem: FoodAlarmModel
        ) = oldItem.alarmId == newItem.alarmId

        override fun areContentsTheSame(
            oldItem: FoodAlarmModel,
            newItem: FoodAlarmModel
        ) = oldItem == newItem
    }
}