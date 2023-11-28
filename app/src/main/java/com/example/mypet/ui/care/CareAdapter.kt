package com.example.mypet.ui.care

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerAlarmHeaderBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerEndBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerFoodBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerRepeatBinding
import com.example.mypet.app.databinding.FragmentCareRecyclerStartBinding
import com.example.mypet.domain.care.CareViewHolderAlarmModel
import com.example.mypet.domain.care.CareViewHolderEndModel
import com.example.mypet.domain.care.CareViewHolderFoodModel
import com.example.mypet.domain.care.CareViewHolderHeaderAlarmModel
import com.example.mypet.domain.care.CareViewHolderModel
import com.example.mypet.domain.care.CareViewHolderRepeatModel
import com.example.mypet.domain.care.CareViewHolderStartModel

class CareAdapter(
    private val careFoodCallback: CareFoodCallback,
    private val careRepeatCallback: CareRepeatCallback,
    private val careAlarmCallback: CareAlarmCallback,
) : ListAdapter<CareViewHolderModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (getItem(viewType)) {
            is CareViewHolderFoodModel -> foodViewHolder(parent)
            is CareViewHolderStartModel -> startViewHolder(parent)
            is CareViewHolderRepeatModel -> repeatViewHolder(parent)
            is CareViewHolderEndModel -> endViewHolder(parent)
            is CareViewHolderHeaderAlarmModel -> alarmHeaderViewHolder(parent)
            is CareViewHolderAlarmModel -> alarmViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is CareViewHolderFoodModel -> (holder as CareFoodViewHolder).bind(item)
            is CareViewHolderStartModel -> (holder as CareStartViewHolder).bind(item)
            is CareViewHolderRepeatModel -> (holder as CareRepeatViewHolder).bind(item)
            is CareViewHolderEndModel -> (holder as CareEndViewHolder).bind(item)
            is CareViewHolderHeaderAlarmModel -> (holder as CareAlarmHeaderViewHolder)
            is CareViewHolderAlarmModel -> (holder as CareAlarmViewHolder).bind(item)
        }
    }

    private fun foodViewHolder(parent: ViewGroup): CareFoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerFoodBinding.inflate(inflater, parent, false)
        return CareFoodViewHolder(binding, careFoodCallback)
    }

    private fun startViewHolder(parent: ViewGroup): CareStartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerStartBinding.inflate(inflater, parent, false)
        return CareStartViewHolder(binding)
    }

    private fun repeatViewHolder(parent: ViewGroup): CareRepeatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerRepeatBinding.inflate(inflater, parent, false)
        return CareRepeatViewHolder(binding, careRepeatCallback)
    }

    private fun endViewHolder(parent: ViewGroup): CareEndViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerEndBinding.inflate(inflater, parent, false)
        return CareEndViewHolder(binding)
    }

    private fun alarmHeaderViewHolder(parent: ViewGroup): CareAlarmHeaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerAlarmHeaderBinding.inflate(inflater, parent, false)
        return CareAlarmHeaderViewHolder(binding)
    }

    private fun alarmViewHolder(parent: ViewGroup): CareAlarmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCareRecyclerAlarmBinding.inflate(inflater, parent, false)
        return CareAlarmViewHolder(binding, careAlarmCallback)
    }

    private class DiffCallback : DiffUtil.ItemCallback<CareViewHolderModel>() {
        override fun areItemsTheSame(
            oldItem: CareViewHolderModel,
            newItem: CareViewHolderModel
        ) = oldItem.key == newItem.key

        override fun areContentsTheSame(
            oldItem: CareViewHolderModel,
            newItem: CareViewHolderModel
        ) = oldItem == newItem
    }
}