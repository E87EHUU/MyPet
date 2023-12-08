package com.example.mypet.ui.care.alarm.detail

import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareAlarmDetailBinding
import com.example.mypet.ui.care.CareViewModel


class CareAlarmDetailFragment : Fragment(R.layout.fragment_care_alarm_detail) {
    private val binding by viewBinding(FragmentCareAlarmDetailBinding::bind)
    private val viewModel by navGraphViewModels<CareViewModel>(R.id.navigationPetCare) { defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListeners()
    }

    private fun initView() {
        viewModel.careAlarmDetailModel?.let { careAlarmDetailModel ->
            binding.switchCareAlarmDetailDelay.isChecked = careAlarmDetailModel.isDelay
            binding.switchCareAlarmDetailVibration.isChecked = careAlarmDetailModel.isVibration
        }
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
            R.string.intent_chooser_ringtone_picker_title
        )
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, null as Uri?)
        chooserRingtoneRegisterForActivityResult.launch(intent)
    }
}