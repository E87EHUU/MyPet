package com.example.mypet.ui.care

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareBinding
import com.example.mypet.domain.care.alarm.CareAlarmDetailModel
import com.example.mypet.ui.care.alarm.CareAlarmCallback
import com.example.mypet.ui.care.main.CareMainCallback
import com.example.mypet.ui.care.repeat.CareRepeatCallback
import com.example.mypet.ui.care.start.CareStartCallback
import com.example.mypet.ui.getToolbar
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

    private val viewModel by navGraphViewModels<CareViewModel>(R.id.navigationPetCare) { defaultViewModelProviderFactory }
    private val args by navArgs<CareFragmentArgs>()

    private val adapter = CareAdapter(this, this, this, this)
    private var isUnlockUI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateCare(args.careId, args.careTypeOrdinal)
    }

    override fun onResume() {
        super.onResume()
        viewModel.careAlarmDetailModel = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserveCareAdapterModels()
    }

    private fun initView() {
        getToolbar()?.let { toolbar ->
            toolbar.title = null
            toolbar.menu.clear()
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
        binding.root.adapter = adapter
    }

    private fun initObserveCareAdapterModels() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.careAdapterModels.collectLatest { mutableList ->
                    adapter.submitList(mutableList)
                    viewModel.careMainModel?.careType?.titleResId?.let {
                        getToolbar()?.title = getString(it)
                    }
                }
            }
        }
    }


    private val datePicker by lazy {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.date_picker_title))
            .setInputMode(INPUT_MODE_CALENDAR)
            .apply {
                viewModel.careStartModel?.timeInMillis?.let {
                    setSelection(it)
                }
            }
            .build()

        datePicker.addOnDismissListener { isUnlockUI = true }
        datePicker.addOnPositiveButtonClickListener {
            viewModel.careStartModel?.timeInMillis = it
            adapter.startViewHolder?.updateDate()
        }
        datePicker
    }

    private val is24HourFormat by lazy {
        android.text.format.DateFormat.is24HourFormat(requireContext())
    }

    private val timePicker by lazy {
        val timePicker = MaterialTimePicker.Builder()
            .setTitleText(getString(R.string.time_picker_title))
            .setInputMode(INPUT_MODE_CLOCK)
            .setTimeFormat(if (is24HourFormat) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)
            .apply {
                viewModel.careStartModel?.hour?.let {
                    setHour(it)
                }

                viewModel.careStartModel?.minute?.let {
                    setMinute(it)
                }
            }
            .build()

        timePicker.addOnDismissListener { isUnlockUI = true }
        timePicker.addOnPositiveButtonClickListener {
            viewModel.careStartModel?.hour = timePicker.hour
            viewModel.careStartModel?.minute = timePicker.minute
            adapter.startViewHolder?.updateTime()
        }
        timePicker
    }

    private fun navToRepeat() {
        findNavController().navigate(R.id.action_careFragment_to_careRepeatDetailFragment)
    }

    private fun navToAlarmDetail() {
        findNavController().navigate(R.id.action_careFragment_to_alarmDetailFragment)
    }

    override fun onClickAlarm(careAlarmDetailModel: CareAlarmDetailModel?) {
        viewModel.careAlarmDetailModel = careAlarmDetailModel
        navToAlarmDetail()
    }

    override fun onSwitchAlarmStart(careAlarmDetailModel: CareAlarmDetailModel) {
        //viewModel.switchAlarmState(alarmMinModel)
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

    private fun saveAndPopBack() {

    }
}