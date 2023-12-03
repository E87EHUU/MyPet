package com.example.mypet.ui.care.alarm.main

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmRecyclerMainBinding
import com.example.mypet.domain.alarm.AlarmMinModel
import com.example.mypet.ui.care.alarm.CareAlarmCallback

class CareAlarmMainViewHolder(
    private val binding: FragmentCareRecyclerAlarmRecyclerMainBinding,
    private val callback: CareAlarmCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var alarmMinModel: AlarmMinModel

    init {
        binding.root.setOnClickListener { callback.onClickAlarm(alarmMinModel) }
    }

    fun bind(alarmMinModel: AlarmMinModel) {
        this.alarmMinModel = alarmMinModel

        with(alarmMinModel) {
            binding.textViewCareRecyclerAlarmTime.text = alarmMinModel.time
        }
    }
}