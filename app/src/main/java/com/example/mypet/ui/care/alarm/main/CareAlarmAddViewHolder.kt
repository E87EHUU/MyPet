package com.example.mypet.ui.care.alarm.main

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmRecyclerAddBinding

class CareAlarmAddViewHolder(
    private val binding: FragmentCareRecyclerAlarmRecyclerAddBinding,
    private val callback: CareAlarmMainCallback,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener { callback.onClickAlarmAdd() }
    }
}