package com.example.mypet.ui.care.start

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.databinding.FragmentCareRecyclerStartBinding
import com.example.mypet.domain.care.CareStartModel
import com.example.mypet.domain.toAppDate

class CareStartViewHolder(
    private val binding: FragmentCareRecyclerStartBinding,
    private val callback: CareStartCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var careStartModel: CareStartModel

    init {
        binding.constraintLayoutCareRecyclerStart.setOnClickListener {
            callback.showDatePicker()
        }
    }

    fun bind(careStartModel: CareStartModel?) {
        careStartModel?.let {
            this.careStartModel = careStartModel

            updateDate()

            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
    }

    fun updateDate() {
        binding.textViewCareRecyclerStartDate.text =
            toAppDate(careStartModel.timeInMillis)
    }
}