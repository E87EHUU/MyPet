package com.example.mypet.service.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.mypet.app.databinding.ServiceAlarmOverlayRecyclerItemBinding
import com.example.mypet.domain.alarm.service.AlarmServiceModel

class AlarmServiceAdapter(
) : ListAdapter<AlarmServiceModel, AlarmServiceAdapterViewHolder>(DiffCallback()) {
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

    private class DiffCallback : DiffUtil.ItemCallback<AlarmServiceModel>() {
        override fun areItemsTheSame(
            oldItem: AlarmServiceModel,
            newItem: AlarmServiceModel
        ) = oldItem.alarmId == newItem.alarmId

        override fun areContentsTheSame(
            oldItem: AlarmServiceModel,
            newItem: AlarmServiceModel
        ) = oldItem == newItem
    }
}