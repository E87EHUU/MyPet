package com.example.mypet.ui.care

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareBinding
import com.example.mypet.domain.alarm.AlarmMinModel
import com.example.mypet.ui.care.alarm.CareAlarmCallback
import com.example.mypet.ui.care.main.CareMainCallback
import com.example.mypet.ui.care.repeat.CareRepeatCallback
import com.example.mypet.ui.care.start.CareStartCallback
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CareFragment : Fragment(R.layout.fragment_care),
    CareMainCallback, CareStartCallback, CareRepeatCallback, CareAlarmCallback {
    private val binding by viewBinding(FragmentCareBinding::bind)
    private val viewModel by viewModels<CareViewModel>()
    private val args by navArgs<CareFragmentArgs>()

    private val adapter = CareAdapter(this, this, this, this)
    private var isUnlockUI = true

    override fun onStart() {
        super.onStart()
        viewModel.updateCare(args.careId, args.careTypeOrdinal)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserveCareAdapterModels()
    }

    private fun initView() {
        binding.root.adapter = adapter

        datePicker.addOnDismissListener { isUnlockUI = true }
        datePicker.addOnPositiveButtonClickListener {
            viewModel.date = it
        }
    }

    private fun initObserveCareAdapterModels() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.careAdapterModels.collectLatest {
                    adapter.submitList(it)
                }
            }
        }
    }


    private val datePicker by lazy {
        MaterialDatePicker.Builder.datePicker()
            .setSelection(viewModel.date)
            .setTitleText(getString(R.string.date_picker_title))
            .setInputMode(INPUT_MODE_CALENDAR)
            .build()
    }

    private val is24HourFormat by lazy {
        android.text.format.DateFormat.is24HourFormat(requireContext())
    }

    private val timePicker by lazy {
        val timePicker = MaterialTimePicker.Builder()
            .setTitleText(getString(R.string.time_picker_title))
            .setInputMode(INPUT_MODE_CLOCK)
            .setHour(viewModel.hour)
            .setMinute(viewModel.minute)
            .setTimeFormat(if (is24HourFormat) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)
            .build()

        timePicker.addOnDismissListener { isUnlockUI = true }
        timePicker.addOnPositiveButtonClickListener {
            viewModel.hour = timePicker.hour
            viewModel.minute = timePicker.minute
            adapter.startViewHolder?.updateTime()
        }
        timePicker
    }

    private fun navToRepeat() {
        val directions = CareFragmentDirections.actionCareFragmentToRepeatFragment()
        findNavController().navigate(directions)
    }

    private fun navToAlarmDetail(alarmMinModel: AlarmMinModel) {
        val directions = CareFragmentDirections
            .actionCareFragmentToAlarmDetailFragment(alarmMinModel.id)
        findNavController().navigate(directions)
    }

    override fun onClickAlarm(alarmMinModel: AlarmMinModel) {
        navToAlarmDetail(alarmMinModel)
    }

    override fun onSwitchAlarmStart(alarmMinModel: AlarmMinModel) {
        viewModel.switchAlarmState(alarmMinModel)
    }

    override fun onClickRepeat() {
        navToRepeat()
    }

    override fun showDatePicker() {
        if (isUnlockUI)
            activity?.supportFragmentManager?.let { fragmentManager ->
                isUnlockUI = false
                datePicker.show(fragmentManager, datePicker.toString())
            }
    }

    override fun showTimePicker() {
        if (isUnlockUI)
            activity?.supportFragmentManager?.let { fragmentManager ->
                isUnlockUI = false
                timePicker.show(fragmentManager, timePicker.toString())
            }
    }
}