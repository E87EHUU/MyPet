package com.example.mypet.ui.food

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
import com.example.mypet.domain.food.CareAlarmHeaderModel
import com.example.mypet.domain.food.CareAlarmModel
import com.example.mypet.domain.food.CareEndModel
import com.example.mypet.domain.food.CareFoodModel
import com.example.mypet.domain.food.CareModel
import com.example.mypet.domain.food.CareRepeatModel
import com.example.mypet.domain.food.CareStartModel

class CareAdapter(
    private val careFoodCallback: CareFoodCallback,
    private val careRepeatCallback: CareRepeatCallback,
    private val careAlarmCallback: CareAlarmCallback,
) : ListAdapter<CareModel, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (getItem(viewType)) {
            is CareFoodModel -> foodViewHolder(parent)
            is CareStartModel -> startViewHolder(parent)
            is CareRepeatModel -> repeatViewHolder(parent)
            is CareEndModel -> endViewHolder(parent)
            is CareAlarmHeaderModel -> alarmHeaderViewHolder(parent)
            is CareAlarmModel -> alarmViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is CareFoodModel -> (holder as CareFoodViewHolder).bind(item)
            is CareStartModel -> (holder as CareStartViewHolder).bind(item)
            is CareRepeatModel -> (holder as CareRepeatViewHolder).bind(item)
            is CareEndModel -> (holder as CareEndViewHolder).bind(item)
            is CareAlarmHeaderModel -> (holder as CareAlarmHeaderViewHolder)
            is CareAlarmModel -> (holder as CareAlarmViewHolder).bind(item)
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