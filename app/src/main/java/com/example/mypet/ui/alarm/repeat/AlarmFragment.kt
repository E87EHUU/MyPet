package com.example.mypet.ui.alarm.repeat

import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentAlarmBinding
import com.example.mypet.ui.food.alarm.FoodAlarmFragmentArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlarmFragment : Fragment(R.layout.fragment_alarm) {
    private val binding by viewBinding(FragmentAlarmBinding::bind)
    private val viewModel by viewModels<AlarmViewModel>()
    private val args by navArgs<AlarmFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init
    }

    private fun observePopBackStack() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                findNavController().currentBackStackEntry?.savedStateHandle?.let { savedStateHandle ->
                    savedStateHandle.getStateFlow<Long?>(ALARM_REPEAT_POP_BACK, null)
                        .collectLatest {
                            it?.let { updateUIRepeatDescription() }
                        }
                }
            }
        }
    }

    private fun prepListeners() {
        binding.includeFoodDetailTopBar.buttonBottomSheetAppBarClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.includeFoodDetailTopBar.buttonBottomSheetAppBarOk.setOnClickListener {
            saveAndPopBack()
        }

        binding.timePickerFoodDetail.setOnTimeChangedListener { _, hourOfDay, minute ->
            viewModel.hour = hourOfDay
            viewModel.minute = minute
        }

        binding.imageViewFoodDetailRepeat.setOnClickListener {

        }

        /*        binding.LayerAlarmSetVibration.setOnClickListener {
                    viewModel.isVibrationChecked = !viewModel.isVibrationChecked
                }

                binding.switchAlarmSetVibration.setOnCheckedChangeListener { _, isChecked ->
                    viewModel.isVibrationChecked = isChecked
                }

                binding.LayerAlarmSetDelay.setOnClickListener {
                    viewModel.isDelayChecked = !viewModel.isDelayChecked
                }

                binding.switchAlarmSetDelay.setOnCheckedChangeListener { _, isChecked ->
                    viewModel.isDelayChecked = isChecked
                }

                binding.LayerAlarmSetMelody.setOnClickListener { launchChooserRingtoneIntent() }
                binding.buttonAlarmSetMelodyNav.setOnClickListener { launchChooserRingtoneIntent() }

                binding.LayerAlarmSetRepeat.setOnClickListener { navToAlarmRepeat() }
                binding.buttonAlarmSetRepeatNav.setOnClickListener { navToAlarmRepeat() }

                binding.textInputEditTextAlarmSetDescription.doAfterTextChanged {
                    viewModel.name = it.toString()
                }*/
    }

    private fun updateUIDelayChecker() {
        binding.switchAlarmSetDelay.isChecked = viewModel.isDelayChecked
    }

    private fun updateUIVibrationChecker() {
        binding.switchAlarmSetVibration.isChecked = viewModel.isVibrationChecked
    }

    private fun updateUIRingtoneDescription() {
        viewModel.ringtonePath?.let {
            val uri = Uri.parse(it)
            val title = RingtoneManager.getRingtone(requireContext(), uri).getTitle(context)

            binding.textViewAlarmSetMelodyDescription.text = title
        }
    }

    private fun updateUIRepeatDescription() {
        with(viewModel) {
            val stringBuilder = StringBuilder()

            if (isMondayChecked && isTuesdayChecked && isWednesdayChecked
                && isThursdayChecked && isFridayChecked && isSaturdayChecked && isSundayChecked
            ) stringBuilder.append(getString(R.string.alarm_repeat_everyday))
            else {
                val divider = ", "

                if (viewModel.isMondayChecked)
                    stringBuilder.append(getString(R.string.mondayShort))

                if (viewModel.isTuesdayChecked) {
                    if (stringBuilder.isNotEmpty())
                        stringBuilder.append(divider)

                    stringBuilder.append(getString(R.string.tuesdayShort))
                }

                if (viewModel.isWednesdayChecked) {
                    if (stringBuilder.isNotEmpty())
                        stringBuilder.append(divider)

                    stringBuilder.append(getString(R.string.wednesdayShort))
                }

                if (viewModel.isThursdayChecked) {
                    if (stringBuilder.isNotEmpty())
                        stringBuilder.append(divider)

                    stringBuilder.append(getString(R.string.thursdayShort))
                }

                if (viewModel.isFridayChecked) {
                    if (stringBuilder.isNotEmpty())
                        stringBuilder.append(divider)

                    stringBuilder.append(getString(R.string.fridayShort))
                }

                if (viewModel.isSaturdayChecked) {
                    if (stringBuilder.isNotEmpty())
                        stringBuilder.append(divider)

                    stringBuilder.append(getString(R.string.saturdayShort))
                }

                if (viewModel.isSundayChecked) {
                    if (stringBuilder.isNotEmpty())
                        stringBuilder.append(divider)

                    stringBuilder.append(getString(R.string.sundayShort))
                }
            }

            binding.textViewAlarmSetRepeatDescription.text =
                stringBuilder.ifEmpty { getString(R.string.alarm_repeat_once) }
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

    private fun saveAndPopBack() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.save().collectLatest {
                launch(Dispatchers.Main) {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun navToAlarmRepeat() {
        findNavController().navigate(R.id.action_foodAlarmFragment_to_alarmRepeatFragment)
    }

    companion object {
        const val ALARM_REPEAT_POP_BACK = "alarm_repeat_pop_back"
    }

}