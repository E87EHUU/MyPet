package com.example.mypet.ui.care.repeat

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareRecyclerRepeatBinding
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.repeat.CareRepeatEndTypes
import com.example.mypet.domain.care.repeat.CareRepeatInterval
import com.example.mypet.domain.toAppDate

class CareRepeatViewHolder(
    private val binding: FragmentCareRecyclerRepeatBinding,
    private val callback: CareRepeatCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private lateinit var careRepeatModel: CareRepeatModel

    init {
        with(binding) {
            constraintLayoutCareRecyclerRepeat.setOnClickListener {
                callback.onClickRepeat()
            }

            constraintLayoutCareRecyclerEnd.setOnClickListener {
                callback.onClickRepeat()
            }
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
    }

    private fun updateUIEnd() {
        with(binding) {
            if (careRepeatModel.endTypeOrdinal == null || careRepeatModel.endTypeOrdinal == CareRepeatEndTypes.NONE.ordinal) {
                textViewCareRecyclerEndTitle.isVisible = false
                imageViewCareRecyclerEndIcon.isVisible = false
                constraintLayoutCareRecyclerEnd.isVisible = false
            } else {
                binding.textViewCareRecyclerEndDescription.text =
                    when (careRepeatModel.endTypeOrdinal) {
                        CareRepeatEndTypes.AFTER_TIMES.ordinal -> context.getString(
                            R.string.care_repeat_end_after_times,
                            careRepeatModel.endAfterTimes.toString()
                        )

                        CareRepeatEndTypes.AFTER_TIME_IN_MILLIS.ordinal -> toAppDate(careRepeatModel.endAfterDate)
                        else -> context.getString(R.string.care_repeat_end_none)
                    }

                textViewCareRecyclerEndTitle.isVisible = true
                imageViewCareRecyclerEndIcon.isVisible = true
                constraintLayoutCareRecyclerEnd.isVisible = true
            }
        }
    }

    private fun updateUIInterval() {
        binding.textViewCareRecyclerRepeatDescription.text =
            when (careRepeatModel.intervalOrdinal) {
                CareRepeatInterval.DAY.ordinal ->
                    context.getString(
                        R.string.care_repeat_interval_description_day,
                        getIntervalTimes()
                    )

                CareRepeatInterval.WEEK.ordinal ->
                    context.getString(
                        R.string.care_repeat_interval_description_week,
                        getIntervalTimes(),
                        getCheckedDay()
                    )

                CareRepeatInterval.MONTH.ordinal ->
                    context.getString(
                        R.string.care_repeat_interval_description_month,
                        getIntervalTimes()
                    )

                CareRepeatInterval.YEAR.ordinal ->
                    context.getString(
                        R.string.care_repeat_interval_description_year,
                        getIntervalTimes()
                    )

                else -> context.getString(R.string.care_repeat_interval_description_none)
            }
    }

    private fun getIntervalTimes() =
        if (careRepeatModel.intervalTimes != 1) careRepeatModel.intervalTimes.toString()
        else ""

    private fun getCheckedDay(): String {
        with(careRepeatModel) {
            if (isMonday || isTuesday || isWednesday
                || isThursday || isFriday || isSaturday || isSunday
            ) {
                val stringBuilder = StringBuilder()
                val divider = ", "

                if (isMonday)
                    stringBuilder.append(context.getString(R.string.alarm_monday_short))

                if (isTuesday) {
                    if (stringBuilder.isNotEmpty())
                        stringBuilder.append(divider)

                    stringBuilder.append(context.getString(R.string.alarm_tuesday_short))
                }

                if (isWednesday) {
                    if (stringBuilder.isNotEmpty())
                        stringBuilder.append(divider)

                    stringBuilder.append(context.getString(R.string.alarm_wednesday_short))
                }

                if (isThursday) {
                    if (stringBuilder.isNotEmpty())
                        stringBuilder.append(divider)

                    stringBuilder.append(context.getString(R.string.alarm_thursday_short))
                }

                if (isFriday) {
                    if (stringBuilder.isNotEmpty())
                        stringBuilder.append(divider)

                    stringBuilder.append(context.getString(R.string.alarm_friday_short))
                }

                if (isSaturday) {
                    if (stringBuilder.isNotEmpty())
                        stringBuilder.append(divider)

                    stringBuilder.append(context.getString(R.string.alarm_saturday_short))
                }

                if (isSunday) {
                    if (stringBuilder.isNotEmpty())
                        stringBuilder.append(divider)

                    stringBuilder.append(context.getString(R.string.alarm_sunday_short))
                }


                return " ($stringBuilder)"
            }
        }
        return ""
    }
}