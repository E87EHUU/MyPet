package com.example.mypet.ui.alarm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentAlarmBinding


class AlarmFragment : Fragment(R.layout.fragment_alarm) {
    private val binding by viewBinding(FragmentAlarmBinding::bind)
    private val viewModel by viewModels<AlarmViewModel>()
    private val args by navArgs<AlarmFragmentArgs>()

    override fun onStart() {
        super.onStart()
        viewModel.update(args)
    }

    override fun onStop() {
        super.onStop()

        findNavController().previousBackStackEntry?.savedStateHandle?.let {
            it[BACK_ALARM_MODEL] = viewModel.alarmModel
        }
        findNavController().popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListeners()
    }

    private fun initView() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            binding.materialCardViewAlarmAccess.isVisible = false
            binding.layerAlarmDelay.isVisible = true
            binding.layerAlarmRingtone.isVisible = true
            binding.layerAlarmVibration.isVisible = true

            binding.switchAlarmDelay.isChecked = viewModel.isDelay
            binding.switchAlarmVibration.isChecked = viewModel.isVibration
        } else {
            binding.materialCardViewAlarmAccess.isVisible = true
            binding.layerAlarmDelay.isVisible = false
            binding.layerAlarmRingtone.isVisible = false
            binding.layerAlarmVibration.isVisible = false
        }
    }

    private fun initListeners() {
        binding.buttonAlarmAccess.setOnClickListener {
            requestPermissionForOverlay()
        }

        binding.layerAlarmVibration.setOnClickListener {
            viewModel.isVibration = !viewModel.isVibration
        }

        binding.switchAlarmVibration.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isVibration = isChecked
        }

        binding.layerAlarmDelay.setOnClickListener {
            viewModel.isDelay = !viewModel.isDelay
        }

        binding.switchAlarmDelay.setOnCheckedChangeListener { _, isChecked ->
            viewModel.isDelay = isChecked
        }

        binding.layerAlarmRingtone.setOnClickListener { launchChooserRingtoneIntent() }
        binding.buttonAlarmRingtoneNav.setOnClickListener { launchChooserRingtoneIntent() }
    }

    private fun updateUIRingtoneDescription() {
        viewModel.ringtonePath?.let {
            val uri = Uri.parse(it)
            val title = RingtoneManager.getRingtone(requireContext(), uri).getTitle(context)

            binding.textViewAlarmRingtoneDescription.text = title
        }
    }

    private val chooserRingtoneRegisterForActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val uri =
                activityResult.data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            viewModel.ringtonePath = uri?.toString()

            updateUIRingtoneDescription()
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

    private fun requestPermissionForOverlay() {
        if (!Settings.canDrawOverlays(requireContext())) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivityForResult(intent, 0)
        }
    }

    companion object {
        const val BACK_ALARM_MODEL = "back_alarm_model"
    }
}