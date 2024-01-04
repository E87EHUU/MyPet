package com.example.mypet.ui.care.alarm.main

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmRecyclerMainBinding
import com.example.mypet.domain.care.alarm.CareAlarmDetailMainModel
import com.example.mypet.domain.toAppTime

class CareAlarmMainViewHolder(
    private val binding: FragmentCareRecyclerAlarmRecyclerMainBinding,
    private val callback: CareAlarmMainCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var careAlarmDetailMainModel: CareAlarmDetailMainModel

    init {
        with(binding) {
            root.setOnClickListener { callback.onClickAlarm(careAlarmDetailMainModel) }

            buttonCareRecyclerAlarmDelete.setOnClickListener {
                callback.onClickAlarmDelete(careAlarmDetailMainModel)
            }
        }
    }

    fun bind(careAlarmDetailMainModel: CareAlarmDetailMainModel) {
        this.careAlarmDetailMainModel = careAlarmDetailMainModel

        with(careAlarmDetailMainModel) {
            binding.textViewCareRecyclerAlarmTime.text = toAppTime(hour, minute)

            description?.let {
                binding.textViewCareRecyclerAlarmDescription.text = it
                binding.textViewCareRecyclerAlarmDescription.isVisible = true
            } ?: run {
                binding.textViewCareRecyclerAlarmDescription.isVisible = false
            }
        }
    }
}