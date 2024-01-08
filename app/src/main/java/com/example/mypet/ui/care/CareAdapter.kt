package com.example.mypet.ui.care

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerEndBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerMainBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerNoteBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerRepeatBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerStartBinding
import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.CareEndModel
import com.example.mypet.domain.care.CareMainModel
import com.example.mypet.domain.care.CareModel
import com.example.mypet.domain.care.CareNoteModel
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.CareStartModel
import com.example.mypet.ui.care.alarm.CareAlarmCallback
import com.example.mypet.ui.care.alarm.CareAlarmViewHolder
import com.example.mypet.ui.care.end.CareEndCallback
import com.example.mypet.ui.care.end.CareEndViewHolder
import com.example.mypet.ui.care.main.CareMainCallback
import com.example.mypet.ui.care.main.CareMainViewHolder
import com.example.mypet.ui.care.note.CareNoteViewHolder
import com.example.mypet.ui.care.repeat.CareRepeatCallback
import com.example.mypet.ui.care.repeat.CareRepeatViewHolder
import com.example.mypet.ui.care.start.CareStartCallback
import com.example.mypet.ui.care.start.CareStartViewHolder

class CareAdapter(
    private val careFoodCallback: CareMainCallback,
    private val careRepeatCallback: CareRepeatCallback,
    private val careAlarmCallback: CareAlarmCallback,
    private val careStartCallback: CareStartCallback,
    private val careEndCallback: CareEndCallback,
) : ListAdapter<CareModel, RecyclerView.ViewHolder>(DiffCallback()) {
    var mainViewHolder: CareMainViewHolder? = null
        private set
    var startViewHolder: CareStartViewHolder? = null
        private set
    var repeatViewHolder: CareRepeatViewHolder? = null
        private set
    var endViewHolder: CareEndViewHolder? = null
        private set
    var alarmViewHolder: CareAlarmViewHolder? = null
        private set
    var noteViewHolder: CareNoteViewHolder? = null
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
            is CareEndModel -> endViewHolder(parent)
            is CareAlarmModel -> alarmViewHolder(parent)
            is CareNoteModel -> noteViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is CareMainModel -> (holder as CareMainViewHolder).bind(item)
            is CareStartModel -> (holder as CareStartViewHolder).bind(item)
            is CareRepeatModel -> (holder as CareRepeatViewHolder).bind(item)
            is CareEndModel -> (holder as CareEndViewHolder).bind(item)
            is CareAlarmModel -> (holder as CareAlarmViewHolder).bind(item)
            is CareNoteModel -> (holder as CareNoteViewHolder).bind(item)
        }
    }

    private fun mainViewHolder(parent: ViewGroup): CareMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerMainBinding.inflate(inflater, parent, false)
        mainViewHolder = CareMainViewHolder(binding, careFoodCallback)
        return mainViewHolder!!
    }

    private fun startViewHolder(parent: ViewGroup): CareStartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerStartBinding.inflate(inflater, parent, false)
        startViewHolder = CareStartViewHolder(binding, careStartCallback)
        return startViewHolder!!
    }

    private fun repeatViewHolder(parent: ViewGroup): CareRepeatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerRepeatBinding.inflate(inflater, parent, false)
        repeatViewHolder = CareRepeatViewHolder(binding, careRepeatCallback)
        return repeatViewHolder!!
    }

    private fun endViewHolder(parent: ViewGroup): CareEndViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerEndBinding.inflate(inflater, parent, false)
        endViewHolder = CareEndViewHolder(binding, careEndCallback)
        return endViewHolder!!
    }

    private fun alarmViewHolder(parent: ViewGroup): CareAlarmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerAlarmBinding.inflate(inflater, parent, false)
        alarmViewHolder = CareAlarmViewHolder(binding, careAlarmCallback)
        return alarmViewHolder!!
    }

    private fun noteViewHolder(parent: ViewGroup): CareNoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerNoteBinding.inflate(inflater, parent, false)
        noteViewHolder = CareNoteViewHolder(binding)
        return noteViewHolder!!
    }

    private class DiffCallback : DiffUtil.ItemCallback<CareModel>() {
        override fun areItemsTheSame(
            oldItem: CareModel,
            newItem: CareModel
        ) = oldItem.key == newItem.key

        override fun areContentsTheSame(
            oldItem: CareModel,
            newItem: CareModel
        ) = oldItem == newItem
    }
}