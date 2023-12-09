package com.example.mypet.ui.care.alarm.main

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmRecyclerMainBinding
import com.example.mypet.domain.care.alarm.CareAlarmDetailModel
import com.example.mypet.domain.toAppTime
import com.example.mypet.ui.care.alarm.CareAlarmCallback

class CareAlarmMainViewHolder(
    private val binding: FragmentCareRecyclerAlarmRecyclerMainBinding,
    private val callback: CareAlarmCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var careAlarmDetailModel: CareAlarmDetailModel

    init {
        binding.root.setOnClickListener { callback.onClickAlarm(careAlarmDetailModel) }
    }

    fun bind(careAlarmDetailModel: CareAlarmDetailModel) {
        this.careAlarmDetailModel = careAlarmDetailModel

        with(careAlarmDetailModel) {
            binding.textViewCareRecyclerAlarmTime.text = toAppTime(hour, minute)
        }
    }
}