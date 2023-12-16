package com.example.mypet.ui.care.alarm.main

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R
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
        binding.buttonCareRecyclerAlarmActive.setOnClickListener {
            careAlarmDetailModel.isActive = !careAlarmDetailModel.isActive
            updateUIActive()
        }
    }

    fun bind(careAlarmDetailModel: CareAlarmDetailModel) {
        this.careAlarmDetailModel = careAlarmDetailModel

        with(careAlarmDetailModel) {
            binding.textViewCareRecyclerAlarmTime.text = toAppTime(hour, minute)

            description?.let {
                binding.textViewCareRecyclerAlarmDescription.text = it
                binding.textViewCareRecyclerAlarmDescription.isVisible = true
            } ?: run {
                binding.textViewCareRecyclerAlarmDescription.isVisible = false
            }

            updateUIActive()
        }
    }

    private fun updateUIActive() {
        if (careAlarmDetailModel.isActive)
            binding.buttonCareRecyclerAlarmActive.setIconResource(R.drawable.baseline_notifications_24)
        else
            binding.buttonCareRecyclerAlarmActive.setIconResource(R.drawable.baseline_notifications_off_24)
    }
}