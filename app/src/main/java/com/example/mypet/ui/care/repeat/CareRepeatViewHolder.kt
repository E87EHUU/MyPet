package com.example.mypet.ui.care.repeat

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareRecyclerRepeatBinding
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.repeat.CareRepeatInterval

class CareRepeatViewHolder(
    private val binding: FragmentCareRecyclerRepeatBinding,
    private val callback: CareRepeatCallback,
) : RecyclerView.ViewHolder(binding.root) {
    private val context = binding.root.context
    private var careRepeatModel: CareRepeatModel? = null

    init {
        with(binding) {
            root.setOnClickListener {
                callback.onClickRepeat()
            }

            buttonCareRecyclerRepeatReset.setOnClickListener {
                callback.onClickReset()
            }
        }
    }

    fun bind(careRepeatModel: CareRepeatModel?) {
        this.careRepeatModel = careRepeatModel

        careRepeatModel?.let {
            updateUI()
            binding.root.isVisible = true
        } ?: run {
            binding.root.isVisible = false
        }
    }

    fun updateUI() {
        careRepeatModel?.let { careRepeatModel ->
            binding.buttonCareRecyclerRepeatReset.isVisible = true

            when (careRepeatModel.intervalOrdinal) {
                CareRepeatInterval.DAY.ordinal -> {
                    binding.textViewCareRecyclerRepeatDescription.text =
                        context.getString(
                            R.string.care_repeat_interval_description_day,
                            getIntervalTimes()
                        )
                }

                CareRepeatInterval.WEEK.ordinal -> {
                    binding.textViewCareRecyclerRepeatDescription.text =
                        context.getString(
                            R.string.care_repeat_interval_description_week,
                            getIntervalTimes(),
                            getCheckedDay()
                        )
                }

                CareRepeatInterval.MONTH.ordinal -> {
                    binding.textViewCareRecyclerRepeatDescription.text =
                        context.getString(
                            R.string.care_repeat_interval_description_month,
                            getIntervalTimes()
                        )
                }

                CareRepeatInterval.YEAR.ordinal -> {
                    binding.textViewCareRecyclerRepeatDescription.text =
                        context.getString(
                            R.string.care_repeat_interval_description_year,
                            getIntervalTimes()
                        )
                }

                else -> updateUIWhenRepeatNotSet()
            }
        } ?: updateUIWhenRepeatNotSet()
    }

    private fun updateUIWhenRepeatNotSet() {
        with(binding) {
            textViewCareRecyclerRepeatDescription.text =
                context.getString(R.string.care_repeat_description_none)
            buttonCareRecyclerRepeatReset.isVisible = false
        }
    }

    private fun getIntervalTimes() =
        careRepeatModel?.let { careRepeatModel ->
            if (careRepeatModel.intervalTimes != 1) careRepeatModel.intervalTimes.toString()
            else ""
        } ?: ""

    private fun getCheckedDay(): String {
        careRepeatModel?.run {
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