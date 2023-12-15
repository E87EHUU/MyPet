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
import com.example.mypet.domain.care.repeat.CareRepeatEndTypes
import com.example.mypet.domain.care.repeat.CareRepeatInterval
import com.example.mypet.domain.toAppDate
import com.example.mypet.ui.care.CareViewModel
import com.example.mypet.ui.getToolbar
import com.google.android.material.datepicker.MaterialDatePicker

class CareRepeatDetailFragment : Fragment(R.layout.fragment_care_repeat_detail) {
    private val binding by viewBinding(FragmentCareRepeatDetailBinding::bind)
    private val viewModel by navGraphViewModels<CareViewModel>(R.id.navigationPetCare) { defaultViewModelProviderFactory }

    private var isUnlockUI = true

    override fun onPause() {
        super.onPause()
        viewModel.careRepeatModel?.let { careRepeatModel ->
            careRepeatModel.intervalTimes =
                binding.textInputEditTextCareRepeatIntervalTimes.text.toString()

            careRepeatModel.endAfterTimes =
                binding.textInputEditTextCareRepeatDetailEndAfterTimes.text.toString()
        }
    }

    override fun onStart() {
        super.onStart()
        initToolbar()
    }

    override fun onStop() {
        super.onStop()
        getToolbar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        updateUI()
        initListeners()
    }

    private fun initToolbar() {
        getToolbar()?.let { toolbar ->
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

    private fun initView() {

    }

    private fun updateUI() {
        viewModel.careRepeatModel?.let { careRepeatModel ->
            binding.textInputEditTextCareRepeatIntervalTimes.setText(careRepeatModel.intervalTimes)
            updateUIInterval()
            updateUIWeekChips()
            updateUIEnd()
            binding.textInputEditTextCareRepeatDetailEndAfterTimes.setText(careRepeatModel.endAfterTimes)
            updateUIEndAfterDateText()
        }
    }

    private fun updateUIEnd() {
        when (viewModel.careRepeatModel?.endTypeOrdinal) {
            CareRepeatEndTypes.AFTER_TIMES.ordinal -> {
                updateUIEndAfterTimes()
                binding.radioButtonCareRepeatDetailEndAfterTimes.isChecked = true
            }

            CareRepeatEndTypes.AFTER_DATE.ordinal -> {
                updateUIEndAfterDate()
                binding.radioButtonCareRepeatDetailEndAfterDate.isChecked = true
            }

            else -> {
                updateUIEndNone()
                binding.radioButtonCareRepeatDetailEndNone.isChecked = true
            }
        }
    }

    private fun updateUIEndAfterTimes() {
        with(binding) {
            radioButtonCareRepeatDetailEndNone.isChecked = false
            radioButtonCareRepeatDetailEndAfterTimes.isChecked = true
            radioButtonCareRepeatDetailEndAfterDate.isChecked = false

            textInputEditTextCareRepeatDetailEndAfterTimes.isEnabled = true
            binding.textInputEditTextCareRepeatEndAfterDate.isEnabled = false
        }
    }

    private fun updateUIEndAfterDate() {
        with(binding) {
            radioButtonCareRepeatDetailEndNone.isChecked = false
            radioButtonCareRepeatDetailEndAfterTimes.isChecked = false
            radioButtonCareRepeatDetailEndAfterDate.isChecked = true

            textInputEditTextCareRepeatDetailEndAfterTimes.isEnabled = false
            binding.textInputEditTextCareRepeatEndAfterDate.isEnabled = true
        }
    }

    private fun updateUIEndNone() {
        with(binding) {
            radioButtonCareRepeatDetailEndNone.isChecked = true
            radioButtonCareRepeatDetailEndAfterTimes.isChecked = false
            radioButtonCareRepeatDetailEndAfterDate.isChecked = false

            textInputEditTextCareRepeatDetailEndAfterTimes.isEnabled = false
            binding.textInputEditTextCareRepeatEndAfterDate.isEnabled = false
        }
    }

    private fun updateUIInterval() {
        when (viewModel.careRepeatModel?.intervalOrdinal) {
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
                CareRepeatInterval.values()[it].titleResId
            )
        }
    }

    private fun updateUIWeekDetail(isVisible: Boolean = false) {
        binding.chipGroupCareRepeatWeek.isVisible = isVisible
    }

    private fun updateUIEndAfterDateText() {
        viewModel.careRepeatModel?.endAfterDate?.let {
            binding.textInputEditTextCareRepeatEndAfterDate.setText(toAppDate(it))
        }
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

        val inputFilterEndAfterTimes = arrayOf(
            InputFilter { source, _, _, dest, _, _ ->
                if (dest.isEmpty() && source == "0") "1"
                else if (dest.length < 2) source
                else ""
            }
        )

        binding.textInputEditTextCareRepeatIntervalTimes.filters = inputFilterIntervalTimes
        binding.textInputEditTextCareRepeatDetailEndAfterTimes.filters = inputFilterEndAfterTimes

        binding.chipGroupCareRepeatWeek.setOnCheckedStateChangeListener { _, checkedIds ->
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

        binding.constraintLayoutCareRepeatDetailEndAfterTimes.setOnClickListener {
            viewModel.careRepeatModel?.let { careRepeatModel ->
                careRepeatModel.endTypeOrdinal = CareRepeatEndTypes.AFTER_TIMES.ordinal
                updateUIEndAfterTimes()
            }
        }

        binding.constraintLayoutCareRepeatDetailEndAfterDate.setOnClickListener {
            viewModel.careRepeatModel?.let { careRepeatModel ->
                careRepeatModel.endTypeOrdinal = CareRepeatEndTypes.AFTER_DATE.ordinal
                updateUIEndAfterDate()
            }
        }

        binding.constraintLayoutCareRepeatDetailEndNone.setOnClickListener {
            viewModel.careRepeatModel?.let { careRepeatModel ->
                careRepeatModel.endTypeOrdinal = CareRepeatEndTypes.NONE.ordinal
                updateUIEndNone()
            }
        }

        binding.textInputEditTextCareRepeatInterval.setOnClickListener { showPopUpRepeatTimes() }
        binding.textInputEditTextCareRepeatEndAfterDate.setOnClickListener { showDatePicker() }
    }

    private fun showPopUpRepeatTimes() {
        val popupMenu = PopupMenu(context, binding.textInputEditTextCareRepeatInterval)
        popupMenu.menuInflater.inflate(R.menu.popup_care_repeat_times, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.care_repeat_times_day -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.intervalOrdinal = CareRepeatInterval.DAY.ordinal
                        updateUIInterval()
                    }
                    true
                }

                R.id.care_repeat_times_week -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.intervalOrdinal = CareRepeatInterval.WEEK.ordinal
                        updateUIInterval()
                    }
                    true
                }

                R.id.care_repeat_times_month -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.intervalOrdinal = CareRepeatInterval.MONTH.ordinal
                        updateUIInterval()
                    }

                    true
                }

                R.id.care_repeat_times_year -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.intervalOrdinal = CareRepeatInterval.YEAR.ordinal
                        updateUIInterval()
                    }
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    private val datePicker by lazy {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.care_date_picker_title))
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .apply {
                viewModel.careRepeatModel?.let {
                    setSelection(it.endAfterDate)
                }
            }
            .build()

        datePicker.addOnDismissListener { isUnlockUI = true }
        datePicker.addOnPositiveButtonClickListener { timeInMillis ->
            viewModel.careRepeatModel?.let {
                it.endAfterDate = timeInMillis
                updateUIEndAfterDateText()
            }
        }
        datePicker
    }

    private fun showDatePicker() {
        if (isUnlockUI)
            activity?.supportFragmentManager?.let { fragmentManager ->
                isUnlockUI = false
                datePicker.show(fragmentManager, datePicker.toString())
            }
    }

    private fun saveAndPopBack() {
        viewModel.careRepeatModel?.let { careRepeatModel ->
            if (careRepeatModel.intervalOrdinal == null)
                careRepeatModel.intervalOrdinal = CareRepeatInterval.DAY.ordinal
        }
        findNavController().popBackStack()
    }
}
