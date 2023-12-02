package com.example.mypet.ui.care

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerMainBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerRepeatBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerStartBinding
import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.CareMainModel
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.CareStartModel
import com.example.mypet.ui.care.alarm.CareAlarmCallback
import com.example.mypet.ui.care.alarm.CareAlarmViewHolder
import com.example.mypet.ui.care.main.CareMainCallback
import com.example.mypet.ui.care.main.CareMainViewHolder
import com.example.mypet.ui.care.repeat.CareRepeatCallback
import com.example.mypet.ui.care.repeat.CareRepeatViewHolder
import com.example.mypet.ui.care.start.CareStartCallback
import com.example.mypet.ui.care.start.CareStartViewHolder

class CareAdapter(
    private val careFoodCallback: CareMainCallback,
    private val careRepeatCallback: CareRepeatCallback,
    private val careAlarmCallback: CareAlarmCallback,
    private val careStartCallback: CareStartCallback,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var careMainModel: CareMainModel? = null
    var careStartModel: CareStartModel? = null
    var careRepeatModel: CareRepeatModel? = null
    var careAlarmModel: CareAlarmModel? = null

    override fun getItemCount() = 4
    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): RecyclerView.ViewHolder {
        return when (position) {
            MAIN_POSITION -> mainViewHolder(parent)
            START_POSITION -> startViewHolder(parent)
            REPEAT_POSITION -> repeatViewHolder(parent)
            else -> alarmViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (position) {
            MAIN_POSITION -> (holder as CareMainViewHolder).bind(careMainModel)
            START_POSITION -> (holder as CareStartViewHolder).bind(careStartModel)
            REPEAT_POSITION -> (holder as CareRepeatViewHolder).bind(careRepeatModel)
            else -> (holder as CareAlarmViewHolder).bind(careAlarmModel)
        }
    }

    private fun mainViewHolder(parent: ViewGroup): CareMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerMainBinding.inflate(inflater, parent, false)
        return CareMainViewHolder(binding, careFoodCallback)
    }

    private fun startViewHolder(parent: ViewGroup): CareStartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerStartBinding.inflate(inflater, parent, false)
        return CareStartViewHolder(binding, careStartCallback)
    }

    private fun repeatViewHolder(parent: ViewGroup): CareRepeatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerRepeatBinding.inflate(inflater, parent, false)
        return CareRepeatViewHolder(binding, careRepeatCallback)
    }

    private fun alarmViewHolder(parent: ViewGroup): CareAlarmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerAlarmBinding.inflate(inflater, parent, false)
        return CareAlarmViewHolder(binding, careAlarmCallback)
    }

    companion object {
        const val MAIN_POSITION = 0
        const val START_POSITION = 1
        const val REPEAT_POSITION = 2
        const val ALARM_POSITION = 4
    }
}