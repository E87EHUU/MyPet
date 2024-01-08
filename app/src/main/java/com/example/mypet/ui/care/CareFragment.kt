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
import com.example.mypet.domain.POST_NOTIFICATIONS
import com.example.mypet.domain.care.alarm.CareAlarmDetailModel
import com.example.mypet.domain.isNotGrantedPermission
import com.example.mypet.domain.requestPermission
import com.example.mypet.ui.care.alarm.CareAlarmCallback
import com.example.mypet.ui.care.end.CareEndCallback
import com.example.mypet.ui.care.main.CareMainCallback
import com.example.mypet.ui.care.repeat.CareRepeatCallback
import com.example.mypet.ui.care.start.CareStartCallback
import com.example.mypet.ui.clear
import com.example.mypet.ui.getToolbar
import com.example.mypet.ui.is24HourFormat
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
    CareMainCallback, CareStartCallback, CareRepeatCallback, CareEndCallback, CareAlarmCallback {
    private val binding by viewBinding(FragmentCareBinding::bind)

    private val viewModel by navGraphViewModels<CareViewModel>(R.id.navigationPetCare) { defaultViewModelProviderFactory }
    private val args by navArgs<CareFragmentArgs>()

    private val adapter = CareAdapter(this, this, this, this, this)
    private var isUnlockUI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        viewModel.updateCare(args.petId, args.careId, args.careTypeOrdinal)
    }

    override fun onResume() {
        super.onResume()
        initToolbar()
        viewModel.careAlarmDetailMainModel = null
    }

    override fun onPause() {
        super.onPause()
        getToolbar()?.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserveCareAdapterModels()
    }

    private fun initView() {
        binding.root.adapter = adapter
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
                updateToolbarTitle()
            }
    }

    private fun initObserveCareAdapterModels() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.careModels.collectLatest { careModels ->
                    adapter.submitList(careModels)
                    startPostponedEnterTransition()
                    updateToolbarTitle()
                }
            }
        }
    }

    private fun updateToolbarTitle() {
        viewModel.careMainModel?.careType?.titleResId?.let {
            getToolbar()?.title = getString(it)
        }
    }

    private val datePicker by lazy {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.care_date_picker_title))
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

    private fun navToRepeat() {
        findNavController().navigate(R.id.action_careFragment_to_careRepeatDetailFragment)
    }

    private fun navToEnd() {
        findNavController().navigate(R.id.action_careFragment_to_careRepeatDetailFragment)
    }

    private fun navToAlarmDetail() {
        findNavController().navigate(R.id.action_careFragment_to_alarmDetailFragment)
    }

    override fun onClickAlarm(careAlarmDetailMainModel: CareAlarmDetailModel?) {
        if (requireActivity().isNotGrantedPermission(POST_NOTIFICATIONS))
            requireActivity().requestPermission(POST_NOTIFICATIONS)

        val alarmModel = careAlarmDetailMainModel ?: CareAlarmDetailModel()
        showTimePicker(alarmModel)
    }

    override fun onClickAlarmDelete(careAlarmDetailMainModel: CareAlarmDetailModel) {
        viewModel.alarmDelete(careAlarmDetailMainModel)
    }

    override fun onClickRepeat() {
        navToRepeat()
    }

    override fun generateDayAlarm(dayTimes: Int) {
        //viewModel.generateDayAlarm(dayTimes)
    }

    override fun showDatePicker() {
        if (isUnlockUI)
            activity?.supportFragmentManager?.let { fragmentManager ->
                isUnlockUI = false
                datePicker.show(fragmentManager, datePicker.toString())
            }
    }

    private fun showTimePicker(careAlarmDetailMainModel: CareAlarmDetailModel) {
        if (isUnlockUI)
            activity?.supportFragmentManager?.let { fragmentManager ->
                isUnlockUI = false

                val timePicker = MaterialTimePicker.Builder()
                    .setTitleText(getString(R.string.care_time_picker_title))
                    .setInputMode(INPUT_MODE_CLOCK)
                    .setHour(careAlarmDetailMainModel.hour)
                    .setMinute(careAlarmDetailMainModel.minute)
                    .setTimeFormat(if (requireActivity().is24HourFormat) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H)
                    .build()

                timePicker.addOnDismissListener { isUnlockUI = true }
                timePicker.addOnPositiveButtonClickListener {
                    viewModel.saveAlarm(
                        careAlarmDetailMainModel = careAlarmDetailMainModel,
                        hour = timePicker.hour,
                        minute = timePicker.minute
                    )
                    adapter.alarmViewHolder?.updateUI()
                }

                timePicker.show(fragmentManager, timePicker.toString())
            }
    }

    private fun saveAndPopBack() {
        viewModel.saveCare()
        findNavController().popBackStack()
    }

    override fun onClickEnd() {
        navToEnd()
    }
}