package com.example.mypet.ui.care.alarm.detail

import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareAlarmDetailBinding
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.domain.care.alarm.CareAlarmDetailMainModel
import com.example.mypet.ui.care.CareViewModel
import com.example.mypet.ui.getToolbar
import com.example.mypet.ui.is24HourFormat


class CareAlarmDetailFragment : Fragment(R.layout.fragment_care_alarm_detail) {
    private val binding by viewBinding(FragmentCareAlarmDetailBinding::bind)
    private val viewModel by navGraphViewModels<CareViewModel>(R.id.navigationPetCare) { defaultViewModelProviderFactory }

    override fun onPause() {
        super.onPause()
        viewModel.careAlarmDetailMainModel?.let { careAlarmDetailModel ->
            careAlarmDetailModel.isDelay = binding.switchCareAlarmDetailDelay.isChecked
            careAlarmDetailModel.isVibration = binding.switchCareAlarmDetailVibration.isChecked
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.careAlarmDetailMainModel == null)
            viewModel.careAlarmDetailMainModel = CareAlarmDetailMainModel()
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

    private fun initView() {
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
        binding.timePickerCareAlarmDetail.setIs24HourView(requireContext().is24HourFormat)
    }

    private fun initListeners() {
        with(binding) {
            timePickerCareAlarmDetail.setOnTimeChangedListener { _, hourOfDay, minute ->
                viewModel.careAlarmDetailMainModel?.let {
                    it.hour = hourOfDay
                    it.minute = minute
                }
            }

            constraintLayoutCareAlarmDetailRingtone.setOnClickListener { launchChooserRingtoneIntent() }

            constraintLayoutCareAlarmDetailVibration.setOnClickListener {
                viewModel.careAlarmDetailMainModel?.let {
                    val isChecked = !switchCareAlarmDetailVibration.isChecked
                    switchCareAlarmDetailVibration.isChecked = isChecked
                    it.isVibration = isChecked
                }
            }

            constraintLayoutCareAlarmDetailDelay.setOnClickListener {
                viewModel.careAlarmDetailMainModel?.let {
                    val isChecked = !switchCareAlarmDetailDelay.isChecked
                    switchCareAlarmDetailDelay.isChecked = isChecked
                    it.isDelay = isChecked
                }
            }

            constraintLayoutCareAlarmDetailNotification.setOnClickListener {
                viewModel.careAlarmDetailMainModel?.let {
                    val isChecked = !switchCareAlarmDetailNotification.isChecked
                    switchCareAlarmDetailNotification.isChecked = isChecked
                    it.isActive = isChecked
                }
            }
        }
    }

    private fun updateUI() {
        viewModel.careAlarmDetailMainModel?.let {
            println(it)
            with(binding) {
                timePickerCareAlarmDetail.hour = it.hour
                timePickerCareAlarmDetail.minute = it.minute
                switchCareAlarmDetailVibration.isChecked = it.isVibration
                switchCareAlarmDetailDelay.isChecked = it.isDelay
                switchCareAlarmDetailNotification.isChecked = it.isActive
            }
        }

        updateUIRingtoneDescription()
    }

    private fun updateUIRingtoneDescription() {
        viewModel.careAlarmDetailMainModel?.let { careAlarmDetailModel ->
            careAlarmDetailModel.ringtonePath?.let {
                val uri = Uri.parse(it)
                val title = RingtoneManager.getRingtone(requireContext(), uri).getTitle(context)

                binding.textViewCareAlarmDetailRingtoneDescription.text = title
            }
        }
    }

    private val chooserRingtoneRegisterForActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            viewModel.careAlarmDetailMainModel?.let { careAlarmDetailModel ->
                val uri =
                    activityResult.data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                careAlarmDetailModel.ringtonePath = uri?.toString()

                updateUIRingtoneDescription()
            }
        }

    private fun launchChooserRingtoneIntent() {
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
        intent.putExtra(
            RingtoneManager.EXTRA_RINGTONE_TITLE,
            R.string.alarm_intent_chooser_ringtone_picker_title
        )
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, null as Uri?)
        chooserRingtoneRegisterForActivityResult.launch(intent)
    }

    private fun saveAndPopBack() {
        viewModel.careAlarmDetailMainModel?.let { careAlarmDetailModel ->
            if (careAlarmDetailModel.id == DEFAULT_ID) {
                viewModel.careAlarmModel?.let { careAlarmModel ->
                    val mutableList = careAlarmModel.alarms.toMutableList()
                    mutableList.add(careAlarmDetailModel)
                    careAlarmModel.alarms = mutableList
                }
            }
        }

        findNavController().popBackStack()
    }
}