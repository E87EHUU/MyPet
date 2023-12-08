package com.example.mypet.ui.care.alarm.main

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentPetRecyclerMainRecyclerAddBinding
import com.example.mypet.ui.care.alarm.CareAlarmCallback

class CareAlarmAddViewHolder(
    private val binding: FragmentPetRecyclerMainRecyclerAddBinding,
    private val callback: CareAlarmCallback,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.root.setOnClickListener { callback.onClickAlarm() }
    }
}