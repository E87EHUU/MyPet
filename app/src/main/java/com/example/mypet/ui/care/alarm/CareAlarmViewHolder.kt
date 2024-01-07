package com.example.mypet.ui.care.alarm

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmBinding
import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.alarm.CareAlarmDetailModel
import com.example.mypet.ui.care.alarm.main.CareAlarmMainAdapter
import com.example.mypet.ui.care.alarm.main.CareAlarmMainCallback

class CareAlarmViewHolder(
    private val binding: FragmentCareRecyclerAlarmBinding,
    private val callback: CareAlarmCallback,
) : RecyclerView.ViewHolder(binding.root), CareAlarmMainCallback {
    private val context = binding.root.context
    private var careAlarmModel: CareAlarmModel? = null

    private val adapter = CareAlarmMainAdapter(this)

    init {
        with(binding) {
            binding.buttonCareRecyclerAlarmAdd.setOnClickListener {
                onClickAlarmAdd()
            }

            recyclerViewCareRecyclerAlarmRecycler.itemAnimator = null
            recyclerViewCareRecyclerAlarmRecycler.adapter = adapter
        }
    }

    fun bind(careAlarmModel: CareAlarmModel? = null) {
        this.careAlarmModel = careAlarmModel
        updateUI()
    }

    fun updateUI() {
        adapter.submitList(careAlarmModel?.alarms)

        careAlarmModel?.let {
            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
    }

    override fun onClickAlarm(careAlarmDetailMainModel: CareAlarmDetailModel) {
        callback.onClickAlarm(careAlarmDetailMainModel)
    }

    override fun onClickAlarmAdd() {
        callback.onClickAlarm()
    }

    override fun onClickAlarmDelete(careAlarmDetailMainModel: CareAlarmDetailModel) {
        callback.onClickAlarmDelete(careAlarmDetailMainModel)
        updateUI()
    }
}