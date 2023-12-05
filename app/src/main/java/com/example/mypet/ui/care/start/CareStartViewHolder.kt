package com.example.mypet.ui.care.start

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerStartBinding
import com.example.mypet.domain.care.CareStartModel
import com.example.mypet.ui.toAppDate
import com.example.mypet.ui.toAppTime

class CareStartViewHolder(
    private val binding: FragmentCareRecyclerStartBinding,
    private val callback: CareStartCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var careStartModel: CareStartModel

    init {
        binding.textViewCareRecyclerStartDate.setOnClickListener {
            callback.showDatePicker()
        }

        binding.textViewCareRecyclerStartTime.setOnClickListener {
            callback.showTimePicker()
        }
    }

    fun bind(careStartModel: CareStartModel?) {
        careStartModel?.let {
            this.careStartModel = careStartModel

            updateDate()
            updateTime()

            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
    }

    fun updateDate() {
        binding.textViewCareRecyclerStartDate.text =
            toAppDate(careStartModel.timeInMillis)
    }

    fun updateTime() {
        binding.textViewCareRecyclerStartTime.text =
            toAppTime(careStartModel.hour, careStartModel.minute)
    }
}