package com.example.mypet.ui.care.alarm.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmRecyclerMainBinding
import com.example.mypet.app.databinding.FragmentPetRecyclerMainRecyclerAddBinding
import com.example.mypet.domain.care.alarm.CareAlarmDetailModel
import com.example.mypet.ui.care.alarm.CareAlarmCallback

class CareAlarmMainAdapter(
    private val callback: CareAlarmCallback,
) : ListAdapter<CareAlarmDetailModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun getItemCount() = super.getItemCount() + 1
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return if (position == itemCount - 1) addViewHolder(parent) else mainViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < itemCount - 1) (holder as CareAlarmMainViewHolder).bind(getItem(position))
    }

    private fun mainViewHolder(parent: ViewGroup): CareAlarmMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerAlarmRecyclerMainBinding.inflate(inflater, parent, false)
        return CareAlarmMainViewHolder(binding, callback)
    }

    private fun addViewHolder(parent: ViewGroup): CareAlarmAddViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPetRecyclerMainRecyclerAddBinding.inflate(inflater, parent, false)
        return CareAlarmAddViewHolder(binding, callback)
    }

    private class DiffCallback : DiffUtil.ItemCallback<CareAlarmDetailModel>() {
        override fun areItemsTheSame(
            oldItem: CareAlarmDetailModel,
            newItem: CareAlarmDetailModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CareAlarmDetailModel,
            newItem: CareAlarmDetailModel
        ) = oldItem == newItem
    }
}