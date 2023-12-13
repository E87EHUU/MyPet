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
import com.example.mypet.domain.care.alarm.CareAlarmDetailModel
import com.example.mypet.ui.care.CareViewModel
import com.example.mypet.ui.getToolbar
import com.example.mypet.ui.is24HourFormat


class CareAlarmDetailFragment : Fragment(R.layout.fragment_care_alarm_detail) {
    private val binding by viewBinding(FragmentCareAlarmDetailBinding::bind)
    private val viewModel by navGraphViewModels<CareViewModel>(R.id.navigationPetCare) { defaultViewModelProviderFactory }

    override fun onPause() {
        super.onPause()
        viewModel.careAlarmDetailModel?.let { careAlarmDetailModel ->
            careAlarmDetailModel.isDelay = binding.switchCareAlarmDetailDelay.isChecked
            careAlarmDetailModel.isVibration = binding.switchCareAlarmDetailVibration.isChecked
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.careAlarmDetailModel == null)
            viewModel.careAlarmDetailModel = CareAlarmDetailModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        updateUI()
        initListeners()
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
        binding.timePickerCareAlarmDetail.setIs24HourView(requireContext().is24HourFormat)
    }

    private fun initListeners() {
        binding.layerCareAlarmDetailVibration.setOnClickListener {
            viewModel.careAlarmDetailModel?.let { careAlarmDetailModel ->
                careAlarmDetailModel.isVibration = !careAlarmDetailModel.isVibration
            }
        }

        binding.switchCareAlarmDetailVibration.setOnCheckedChangeListener { _, isChecked ->
            viewModel.careAlarmDetailModel?.let { careAlarmDetailModel ->
                careAlarmDetailModel.isVibration = isChecked
            }
        }

        binding.layerCareAlarmDetailCareDelay.setOnClickListener {
            viewModel.careAlarmDetailModel?.let { careAlarmDetailModel ->
                careAlarmDetailModel.isDelay = !careAlarmDetailModel.isDelay
            }
        }

        binding.switchCareAlarmDetailDelay.setOnCheckedChangeListener { _, isChecked ->
            viewModel.careAlarmDetailModel?.let { careAlarmDetailModel ->
                careAlarmDetailModel.isDelay = isChecked
            }
        }

        binding.layerCareAlarmDetailRingtone.setOnClickListener { launchChooserRingtoneIntent() }

        binding.timePickerCareAlarmDetail.setOnTimeChangedListener { _, hourOfDay, minute ->
            viewModel.careAlarmDetailModel?.let { careAlarmDetailModel ->
                careAlarmDetailModel.hour = hourOfDay
                careAlarmDetailModel.minute = minute
            }
        }
    }

    private fun updateUI() {
        viewModel.careAlarmDetailModel?.let {
            binding.timePickerCareAlarmDetail.hour = it.hour
            binding.timePickerCareAlarmDetail.minute = it.minute
            binding.switchCareAlarmDetailVibration.isChecked = it.isVibration
            binding.switchCareAlarmDetailDelay.isChecked = it.isDelay
        }

        updateUIRingtoneDescription()
    }

    private fun updateUIRingtoneDescription() {
        viewModel.careAlarmDetailModel?.let { careAlarmDetailModel ->
            careAlarmDetailModel.ringtonePath?.let {
                val uri = Uri.parse(it)
                val title = RingtoneManager.getRingtone(requireContext(), uri).getTitle(context)

                binding.textViewCareAlarmDetailRingtoneDescription.text = title
            }
        }
    }

    private val chooserRingtoneRegisterForActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            viewModel.careAlarmDetailModel?.let { careAlarmDetailModel ->
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
        viewModel.careAlarmDetailModel?.let { careAlarmDetailModel ->
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