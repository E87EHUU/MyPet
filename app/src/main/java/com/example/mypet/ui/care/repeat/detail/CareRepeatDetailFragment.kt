package com.example.mypet.ui.care.repeat.detail

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareRepeatDetailBinding
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.repeat.CareRepeatInterval
import com.example.mypet.ui.care.CareViewModel
import com.example.mypet.ui.clear
import com.example.mypet.ui.getToolbar

class CareRepeatDetailFragment : Fragment(R.layout.fragment_care_repeat_detail) {
    private val binding by viewBinding(FragmentCareRepeatDetailBinding::bind)
    private val viewModel by navGraphViewModels<CareViewModel>(R.id.navigationPetCare) { defaultViewModelProviderFactory }

    override fun onPause() {
        super.onPause()

        viewModel.careRepeatModel?.let { careRepeatModel ->
            careRepeatModel.intervalTimes =
                try {
                    binding.textInputEditTextCareRepeatIntervalTimes.text.toString().toInt()
                } catch (_: Exception) {
                    null
                }
        }
    }

    override fun onStart() {
        super.onStart()
        initToolbar()
    }

    override fun onStop() {
        super.onStop()
        getToolbar()?.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        initListeners()
    }

    private fun initToolbar() {
        getToolbar()
            ?.clear()
            ?.let { toolbar ->
            toolbar.inflateMenu(R.menu.toolbar_save)
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.toolbarSave -> {
                        saveAndPopBack()
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun updateUI() {
        viewModel.careRepeatModel?.let { careRepeatModel ->
            careRepeatModel.updateUIInterval()
            updateUIWeekChips()
        }
    }

    private fun CareRepeatModel.updateUIInterval() {
        intervalTimes?.let {
            binding.textInputEditTextCareRepeatIntervalTimes.setText(it.toString())
        }

        when (intervalOrdinal) {
            CareRepeatInterval.WEEK.ordinal -> {
                updateUIIntervalText()
                updateUIWeekDetail(isVisible = true)
            }

            CareRepeatInterval.MONTH.ordinal -> {
                updateUIIntervalText()
                updateUIWeekDetail()
            }

            CareRepeatInterval.YEAR.ordinal -> {
                updateUIIntervalText()
                updateUIWeekDetail()
            }

            else -> {
                updateUIIntervalText()
                updateUIWeekDetail()
            }
        }
    }

    private fun updateUIIntervalText() {
        viewModel.careRepeatModel?.intervalOrdinal?.let {
            binding.textInputEditTextCareRepeatInterval.setText(
                CareRepeatInterval.entries[it].titleResId
            )
        }
    }

    private fun updateUIWeekDetail(isVisible: Boolean = false) {
        binding.chipGroupCareRepeatWeek.isVisible = isVisible
    }

    private fun updateUIWeekChips() {
        viewModel.careRepeatModel?.let { careRepeatModel ->
            binding.chipCareRepeatMonday.isChecked = careRepeatModel.isMonday
            binding.chipCareRepeatTuesday.isChecked = careRepeatModel.isTuesday
            binding.chipCareRepeatWednesday.isChecked = careRepeatModel.isWednesday
            binding.chipCareRepeatThursday.isChecked = careRepeatModel.isThursday
            binding.chipCareRepeatFriday.isChecked = careRepeatModel.isFriday
            binding.chipCareRepeatSaturday.isChecked = careRepeatModel.isSaturday
            binding.chipCareRepeatSunday.isChecked = careRepeatModel.isSunday
        }
    }

    private fun initListeners() {
        val inputFilterIntervalTimes = arrayOf(
            InputFilter { source, _, _, dest, _, _ ->
                if (dest.isEmpty() && source == "0") "1"
                else if (dest.length < 2) source
                else ""
            }
        )

        with(binding) {
            textInputEditTextCareRepeatIntervalTimes.filters = inputFilterIntervalTimes

            chipGroupCareRepeatWeek.setOnCheckedStateChangeListener { _, checkedIds ->
                viewModel.careRepeatModel?.let { careRepeatModel ->
                    careRepeatModel.isMonday = checkedIds.contains(R.id.chipCareRepeatMonday)
                    careRepeatModel.isTuesday = checkedIds.contains(R.id.chipCareRepeatTuesday)
                    careRepeatModel.isWednesday = checkedIds.contains(R.id.chipCareRepeatWednesday)
                    careRepeatModel.isThursday = checkedIds.contains(R.id.chipCareRepeatThursday)
                    careRepeatModel.isFriday = checkedIds.contains(R.id.chipCareRepeatFriday)
                    careRepeatModel.isSaturday = checkedIds.contains(R.id.chipCareRepeatSaturday)
                    careRepeatModel.isSunday = checkedIds.contains(R.id.chipCareRepeatSunday)
                }
            }

            textInputEditTextCareRepeatInterval.setOnClickListener { showPopUpRepeatTimes() }
        }
    }

    private fun showPopUpRepeatTimes() {
        val popupMenu = PopupMenu(context, binding.textInputEditTextCareRepeatInterval)
        popupMenu.menuInflater.inflate(R.menu.popup_care_repeat_times, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.care_repeat_times_day -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.intervalOrdinal = CareRepeatInterval.DAY.ordinal
                        careRepeatModel.updateUIInterval()
                    }
                    true
                }

                R.id.care_repeat_times_week -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.intervalOrdinal = CareRepeatInterval.WEEK.ordinal
                        careRepeatModel.updateUIInterval()
                    }
                    true
                }

                R.id.care_repeat_times_month -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.intervalOrdinal = CareRepeatInterval.MONTH.ordinal
                        careRepeatModel.updateUIInterval()
                    }

                    true
                }

                R.id.care_repeat_times_year -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.intervalOrdinal = CareRepeatInterval.YEAR.ordinal
                        careRepeatModel.updateUIInterval()
                    }
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    private fun saveAndPopBack() {
        viewModel.careRepeatModel?.let { careRepeatModel ->
            if (careRepeatModel.intervalOrdinal == null)
                careRepeatModel.intervalOrdinal = CareRepeatInterval.DAY.ordinal
        }
        findNavController().popBackStack()
    }
}
