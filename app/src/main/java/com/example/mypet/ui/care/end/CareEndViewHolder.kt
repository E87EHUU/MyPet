package com.example.mypet.ui.care.end

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareRecyclerEndBinding
import com.example.mypet.domain.care.CareEndModel
import com.example.mypet.domain.care.end.CareEndTypes
import com.example.mypet.domain.toAppDate

class CareEndViewHolder(
    private val binding: FragmentCareRecyclerEndBinding,
    private val callback: CareEndCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private var careEndModel: CareEndModel? = null

    init {
        binding.root.setOnClickListener {
            callback.onClickEnd()
        }
    }

    fun bind(careEndModel: CareEndModel?) {
        this.careEndModel = careEndModel

        careEndModel?.let {
            updateUI()
            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
    }

    private fun updateUI() {
        careEndModel?.let { careEndModel ->
            binding.textViewCareRecyclerEndDescription.text =
                when (careEndModel.typeOrdinal) {
                    CareEndTypes.AFTER_TIMES.ordinal ->
                        context.getString(
                            R.string.care_repeat_end_after_times,
                            careEndModel.afterTimes.toString()
                        )

                    CareEndTypes.AFTER_TIME_IN_MILLIS.ordinal ->
                        toAppDate(careEndModel.afterDate)

                    else ->
                        context.getString(R.string.care_end_none)
                }
        }
    }
}