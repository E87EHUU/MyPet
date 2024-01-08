package com.example.mypet.ui.care.alarm.main

import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmRecyclerMainBinding
import com.example.mypet.domain.care.alarm.CareAlarmDetailModel
import com.example.mypet.domain.toAppTime

class CareAlarmMainViewHolder(
    private val binding: FragmentCareRecyclerAlarmRecyclerMainBinding,
    private val callback: CareAlarmMainCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var careAlarmDetailMainModel: CareAlarmDetailModel

    init {
        with(binding) {
            root.setOnClickListener { callback.onClickAlarm(careAlarmDetailMainModel) }

            buttonCareRecyclerAlarmDelete.setOnClickListener {
                callback.onClickAlarmDelete(careAlarmDetailMainModel)
            }
        }
    }

    fun bind(careAlarmDetailMainModel: CareAlarmDetailModel) {
        this.careAlarmDetailMainModel = careAlarmDetailMainModel

        with(careAlarmDetailMainModel) {
            binding.textViewCareRecyclerAlarmTime.text = toAppTime(hour, minute)
        }
    }
}