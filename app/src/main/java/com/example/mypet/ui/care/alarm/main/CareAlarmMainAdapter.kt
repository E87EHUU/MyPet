package com.example.mypet.ui.care.alarm.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmRecyclerMainBinding
import com.example.mypet.domain.care.alarm.CareAlarmDetailModel

class CareAlarmMainAdapter(
    private val callback: CareAlarmMainCallback,
) : ListAdapter<CareAlarmDetailModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun submitList(list: List<CareAlarmDetailModel>?) {
        val mutableList = list?.toMutableList()
            ?.apply {
                sortBy {
                    val sum = it.hour * 60 + it.minute
                    sum
                }
            }
        super.submitList(mutableList)
    }

    override fun getItemViewType(position: Int) = position
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