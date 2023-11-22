package com.example.mypet.ui.alarm.repeat

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentAlarmBinding
import com.example.mypet.app.databinding.FragmentAlarmRepeatWeekBinding
import com.example.mypet.ui.alarm.AlarmViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmRepeatWeekFragment :
    BottomSheetDialogFragment(R.layout.fragment_alarm_repeat_week) {
    private val binding by viewBinding(FragmentAlarmRepeatWeekBinding::bind)
    private val viewModel by navGraphViewModels<AlarmViewModel>(R.id.navigationAlarm) { defaultViewModelProviderFactory }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                ?.let {
                    val behavior = BottomSheetBehavior.from(it)
                    it.updateLayoutParams {
                        val displayHeight = requireActivity().resources.displayMetrics.heightPixels
                        height = displayHeight
                    }
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    behavior.isDraggable = false
                }
        }

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAlarmRepeatClose.setOnClickListener {
            navToPopBackStack()
        }

        updateUI()
        setListeners()
    }

    private fun updateUI() {
        updateUIIsMondayChecked()
        updateUIIsTuesdayChecked()
        updateUIIsWednesdayChecked()
        updateUIIsThursdayChecked()
        updateUIIsFridayChecked()
        updateUIIsSaturdayChecked()
        updateUIIsSundayChecked()
    }

    private fun updateUIIsMondayChecked() {
        binding.checkBoxAlarmRepeatMonday.isChecked = viewModel.isMondayChecked
    }

    private fun updateUIIsTuesdayChecked() {
        binding.checkBoxAlarmRepeatTuesday.isChecked = viewModel.isTuesdayChecked
    }

    private fun updateUIIsWednesdayChecked() {
        binding.checkBoxAlarmRepeatWednesday.isChecked = viewModel.isWednesdayChecked
    }

    private fun updateUIIsThursdayChecked() {
        binding.checkBoxAlarmRepeatThursday.isChecked = viewModel.isThursdayChecked
    }

    private fun updateUIIsFridayChecked() {
        binding.checkBoxAlarmRepeatFriday.isChecked = viewModel.isFridayChecked
    }

    private fun updateUIIsSaturdayChecked() {
        binding.checkBoxAlarmRepeatSaturday.isChecked = viewModel.isSaturdayChecked
    }

    private fun updateUIIsSundayChecked() {
        binding.checkBoxAlarmRepeatSunday.isChecked = viewModel.isSundayChecked
    }

    private fun setListeners() {
        binding.layerAlarmRepeatMonday.setOnClickListener {
            viewModel.isMondayChecked = !viewModel.isMondayChecked
            updateUIIsMondayChecked()
        }

        binding.checkBoxAlarmRepeatMonday.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isMondayChecked = isChecked
            updateUIIsMondayChecked()
        }

        binding.layerAlarmRepeatTuesday.setOnClickListener {
            viewModel.isTuesdayChecked = !viewModel.isTuesdayChecked
            updateUIIsTuesdayChecked()
        }

        binding.checkBoxAlarmRepeatTuesday.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isTuesdayChecked = isChecked
            updateUIIsTuesdayChecked()
        }

        binding.layerAlarmRepeatWednesday.setOnClickListener {
            viewModel.isWednesdayChecked = !viewModel.isWednesdayChecked
            updateUIIsWednesdayChecked()
        }

        binding.checkBoxAlarmRepeatWednesday.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isWednesdayChecked = isChecked
            updateUIIsWednesdayChecked()
        }

        binding.layerAlarmRepeatThursday.setOnClickListener {
            viewModel.isThursdayChecked = !viewModel.isThursdayChecked
            updateUIIsThursdayChecked()
        }

        binding.checkBoxAlarmRepeatThursday.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isThursdayChecked = isChecked
            updateUIIsThursdayChecked()
        }

        binding.layerAlarmRepeatFriday.setOnClickListener {
            viewModel.isFridayChecked = !viewModel.isFridayChecked
            updateUIIsFridayChecked()
        }

        binding.checkBoxAlarmRepeatFriday.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isFridayChecked = isChecked
            updateUIIsFridayChecked()
        }

        binding.layerAlarmRepeatSaturday.setOnClickListener {
            viewModel.isSaturdayChecked = !viewModel.isSaturdayChecked
            updateUIIsSaturdayChecked()
        }

        binding.checkBoxAlarmRepeatSaturday.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isSaturdayChecked = isChecked
            updateUIIsSaturdayChecked()
        }

        binding.layerAlarmRepeatSunday.setOnClickListener {
            viewModel.isSundayChecked = !viewModel.isSundayChecked
            updateUIIsSundayChecked()
        }

        binding.checkBoxAlarmRepeatSunday.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isSundayChecked = isChecked
            updateUIIsSundayChecked()
        }
    }

    private fun navToPopBackStack() {
        findNavController().previousBackStackEntry?.savedStateHandle?.let {
            //it[ALARM_REPEAT_POP_BACK] = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        }
        findNavController().popBackStack()
    }
}