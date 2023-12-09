package com.example.mypet.ui.care.repeat

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareRecyclerRepeatBinding
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.repeat.CareRepeatEndTypes
import com.example.mypet.domain.care.repeat.CareRepeatInterval
import com.example.mypet.ui.toAppDate

class CareRepeatViewHolder(
    private val binding: FragmentCareRecyclerRepeatBinding,
    private val callback: CareRepeatCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private lateinit var careRepeatModel: CareRepeatModel

    init {
        binding.root.setOnClickListener {
            callback.onClickRepeat()
        }
    }

    fun bind(careRepeatModel: CareRepeatModel?) {
        careRepeatModel?.let {
            this.careRepeatModel = careRepeatModel

            updateUIInterval()
            updateUIEnd()

            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
        //TODO дописать функуию отображения информации о повторе в зависимости от данных. Как пример, если установлены понедельник, вторник то "Каждый Пн, Вт", если через неделю то "Каждую 2 неделю по Пн, Вт". в таком роде, надо хорошо так подумать.
    }

    private fun updateUIEnd() {
        if (careRepeatModel.endTypeOrdinal == CareRepeatEndTypes.NONE.ordinal) {
            binding.groupCareRecyclerEnd.isVisible = false
        } else {
            binding.textViewCareRecyclerEndDescription.text =
                when (careRepeatModel.endTypeOrdinal) {
                    CareRepeatEndTypes.AFTER_TIMES.ordinal -> context.getString(
                        R.string.care_repeat_end_after_times,
                        careRepeatModel.endAfterTimes
                    )

                    CareRepeatEndTypes.AFTER_DATE.ordinal -> toAppDate(careRepeatModel.endAfterDate)
                    else -> context.getString(R.string.care_repeat_end_none)
                }

            binding.groupCareRecyclerEnd.isVisible = true
        }
    }

    private fun updateUIInterval() {
        binding.textViewCareRecyclerRepeatDescription.text =
            when (careRepeatModel.intervalOrdinal) {
                CareRepeatInterval.DAY.ordinal -> {
                    val day =
                        if (careRepeatModel.intervalTimes != "1") careRepeatModel.intervalTimes
                        else ""

                    context.getString(R.string.care_repeat_interval_description_day, day)
                }

                CareRepeatInterval.WEEK.ordinal -> {
                    val week =
                        if (careRepeatModel.intervalTimes != "1") careRepeatModel.intervalTimes
                        else ""

                    context.getString(R.string.care_repeat_interval_description_week, week)
                }

                CareRepeatInterval.MONTH.ordinal -> {
                    val month =
                        if (careRepeatModel.intervalTimes != "1") careRepeatModel.intervalTimes
                        else ""

                    context.getString(R.string.care_repeat_interval_description_month, month)
                }

                CareRepeatInterval.YEAR.ordinal -> {
                    val year =
                        if (careRepeatModel.intervalTimes != "1") careRepeatModel.intervalTimes
                        else ""

                    context.getString(R.string.care_repeat_interval_description_year, year)
                }
                else -> context.getString(R.string.care_repeat_interval_description_none)
            }
    }
}