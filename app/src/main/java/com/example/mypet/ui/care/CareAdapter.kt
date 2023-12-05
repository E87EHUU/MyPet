package com.example.mypet.ui.care

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerMainBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerRepeatBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerStartBinding
import com.example.mypet.domain.care.CareAdapterModel
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
) : ListAdapter<CareAdapterModel, RecyclerView.ViewHolder>(DiffCallback()) {
    var mainBinding: FragmentCareRecyclerMainBinding? = null
        private set
    var startViewHolder: CareStartViewHolder? = null
        private set
    var repeatBinding: FragmentCareRecyclerRepeatBinding? = null
        private set

    override fun getItemViewType(position: Int) = position
    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): RecyclerView.ViewHolder {
        return when (getItem(position)) {
            is CareMainModel -> mainViewHolder(parent)
            is CareStartModel -> startViewHolder(parent)
            is CareRepeatModel -> repeatViewHolder(parent)
            is CareAlarmModel -> alarmViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is CareMainModel -> (holder as CareMainViewHolder).bind(item)
            is CareStartModel -> (holder as CareStartViewHolder).bind(item)
            is CareRepeatModel -> (holder as CareRepeatViewHolder).bind(item)
            is CareAlarmModel -> (holder as CareAlarmViewHolder).bind(item)
        }
    }

    private fun mainViewHolder(parent: ViewGroup): CareMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mainBinding = FragmentCareRecyclerMainBinding.inflate(inflater, parent, false)
        return CareMainViewHolder(mainBinding!!, careFoodCallback)
    }

    private fun startViewHolder(parent: ViewGroup): CareStartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerStartBinding.inflate(inflater, parent, false)
        startViewHolder = CareStartViewHolder(binding, careStartCallback)
        return startViewHolder!!
    }

    private fun repeatViewHolder(parent: ViewGroup): CareRepeatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        repeatBinding = FragmentCareRecyclerRepeatBinding.inflate(inflater, parent, false)
        return CareRepeatViewHolder(repeatBinding!!, careRepeatCallback)
    }

    private fun alarmViewHolder(parent: ViewGroup): CareAlarmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerAlarmBinding.inflate(inflater, parent, false)
        return CareAlarmViewHolder(binding, careAlarmCallback)
    }

    private class DiffCallback : DiffUtil.ItemCallback<CareAdapterModel>() {
        override fun areItemsTheSame(
            oldItem: CareAdapterModel,
            newItem: CareAdapterModel
        ) = oldItem.key == newItem.key

        override fun areContentsTheSame(
            oldItem: CareAdapterModel,
            newItem: CareAdapterModel
        ) = oldItem == newItem
    }
}