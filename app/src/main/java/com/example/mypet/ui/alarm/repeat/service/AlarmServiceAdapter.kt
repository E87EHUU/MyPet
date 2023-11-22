package com.example.mypet.ui.alarm.repeat.service

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mypet.app.databinding.ServiceAlarmOverlayRecyclerItemBinding
import com.example.mypet.data.local.room.model.alarm.LocalAlarmServiceModel

class AlarmServiceAdapter(
) : ListAdapter<LocalAlarmServiceModel, AlarmServiceAdapterViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmServiceAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ServiceAlarmOverlayRecyclerItemBinding.inflate(inflater, parent, false)
        return AlarmServiceAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmServiceAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<LocalAlarmServiceModel>() {
        override fun areItemsTheSame(
            oldItem: LocalAlarmServiceModel,
            newItem: LocalAlarmServiceModel
        ) = oldItem.alarmId == newItem.alarmId

        override fun areContentsTheSame(
            oldItem: LocalAlarmServiceModel,
            newItem: LocalAlarmServiceModel
        ) = oldItem == newItem
    }
}