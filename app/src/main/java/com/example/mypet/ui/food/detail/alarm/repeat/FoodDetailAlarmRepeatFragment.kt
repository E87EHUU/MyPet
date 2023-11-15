package com.example.mypet.ui.food.detail.alarm.repeat

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentFoodDetailAlarmRepeatBinding
import com.example.mypet.ui.food.detail.alarm.FoodDetailAlarmFragment.Companion.ALARM_REPEAT_POP_BACK
import com.example.mypet.ui.food.detail.alarm.FoodDetailAlarmViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.ZoneOffset

@AndroidEntryPoint
class FoodDetailAlarmRepeatFragment :
    BottomSheetDialogFragment(R.layout.fragment_food_detail_alarm_repeat) {
    private val binding by viewBinding(FragmentFoodDetailAlarmRepeatBinding::bind)
    private val viewModel by navGraphViewModels<FoodDetailAlarmViewModel>(R.id.navigationFoodAlarm) { defaultViewModelProviderFactory }

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
            it[ALARM_REPEAT_POP_BACK] = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        }
        findNavController().popBackStack()
    }
}