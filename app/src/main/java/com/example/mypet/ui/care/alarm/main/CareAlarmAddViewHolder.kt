package com.example.mypet.ui.care.alarm.main

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmRecyclerAddBinding
import com.example.mypet.ui.care.alarm.CareAlarmCallback

class CareAlarmAddViewHolder(
    private val binding: FragmentCareRecyclerAlarmRecyclerAddBinding,
    private val callback: CareAlarmCallback,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener { callback.onClickAlarm() }
    }
}