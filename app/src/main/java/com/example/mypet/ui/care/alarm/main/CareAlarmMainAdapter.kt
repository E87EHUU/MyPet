package com.example.mypet.ui.care.alarm.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmRecyclerAddBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmRecyclerMainBinding
import com.example.mypet.domain.care.alarm.CareAlarmDetailAddModel
import com.example.mypet.domain.care.alarm.CareAlarmDetailMainModel
import com.example.mypet.domain.care.alarm.CareAlarmDetailModel

class CareAlarmMainAdapter(
    private val callback: CareAlarmMainCallback,
) : ListAdapter<CareAlarmDetailModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun getItemViewType(position: Int) = position
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when (getItem(position)) {
            is CareAlarmDetailMainModel -> mainViewHolder(parent)
            is CareAlarmDetailAddModel -> addViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is CareAlarmDetailMainModel -> (holder as CareAlarmMainViewHolder).bind(item)
            is CareAlarmDetailAddModel -> {}
        }
    }

    private fun mainViewHolder(parent: ViewGroup): CareAlarmMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerAlarmRecyclerMainBinding.inflate(inflater, parent, false)
        return CareAlarmMainViewHolder(binding, callback)
    }

    private fun addViewHolder(parent: ViewGroup): CareAlarmAddViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerAlarmRecyclerAddBinding.inflate(inflater, parent, false)
        return CareAlarmAddViewHolder(binding, callback)
    }

    private class DiffCallback : DiffUtil.ItemCallback<CareAlarmDetailModel>() {
        override fun areItemsTheSame(
            oldItem: CareAlarmDetailModel,
            newItem: CareAlarmDetailModel
        ) = (oldItem.hour * 60 + oldItem.minute) == (newItem.hour * 60 + newItem.minute)

        override fun areContentsTheSame(
            oldItem: CareAlarmDetailModel,
            newItem: CareAlarmDetailModel
        ) = oldItem == newItem
    }
}