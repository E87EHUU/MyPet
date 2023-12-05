package com.example.mypet.ui.care.alarm.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmRecyclerMainBinding
import com.example.mypet.domain.alarm.AlarmMinModel
import com.example.mypet.ui.care.alarm.CareAlarmCallback

class CareAlarmMainAdapter(
    private val callback: CareAlarmCallback,
) : ListAdapter<AlarmMinModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return mainViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CareAlarmMainViewHolder).bind(getItem(position))
    }

    private fun mainViewHolder(parent: ViewGroup): CareAlarmMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerAlarmRecyclerMainBinding.inflate(inflater, parent, false)
        return CareAlarmMainViewHolder(binding, callback)
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