package com.example.mypet.ui.care.alarm

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmBinding
import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.alarm.CareAlarmDetailMainModel
import com.example.mypet.ui.care.alarm.main.CareAlarmMainAdapter
import com.example.mypet.ui.care.alarm.main.CareAlarmMainCallback

class CareAlarmViewHolder(
    private val binding: FragmentCareRecyclerAlarmBinding,
    private val callback: CareAlarmCallback,
) : RecyclerView.ViewHolder(binding.root), CareAlarmMainCallback {
    private val context = binding.root.context
    private lateinit var careAlarmModel: CareAlarmModel

    private val adapter = CareAlarmMainAdapter(this)

    init {
        with(binding) {
            recyclerViewCareRecyclerAlarmRecycler.itemAnimator = null
            recyclerViewCareRecyclerAlarmRecycler.adapter = adapter
        }
    }

    fun bind(careAlarmModel: CareAlarmModel?) {
        careAlarmModel?.let {
            this.careAlarmModel = careAlarmModel

            adapter.submitList(careAlarmModel.alarms)

            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
    }

    override fun onClickAlarm(careAlarmDetailMainModel: CareAlarmDetailMainModel?) {
        callback.onClickAlarm(careAlarmDetailMainModel)
    }

    override fun onClickDelete(careAlarmDetailMainModel: CareAlarmDetailMainModel) {
        callback.onClickDelete(careAlarmDetailMainModel)
        adapter.submitList(careAlarmModel.alarms)
    }
}