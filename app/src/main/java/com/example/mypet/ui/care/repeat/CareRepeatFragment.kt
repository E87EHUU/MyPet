package com.example.mypet.ui.care.repeat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareRepeatBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CareRepeatFragment : Fragment(R.layout.fragment_care_repeat) {
    private val binding by viewBinding(FragmentCareRepeatBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observePopBackStack()

    }

    private fun observePopBackStack() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                findNavController().currentBackStackEntry?.savedStateHandle?.let { savedStateHandle ->
                    savedStateHandle.getStateFlow<Long?>(ALARM_REPEAT_POP_BACK, null)
                        .collectLatest {
                            it?.let {
                            //    updateUIRepeatDescription()
                            }
                        }
                }
            }
        }
    }



  /*  private fun updateUIRepeatDescription() {
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

    private fun navToAlarmRepeatWeek() {
        findNavController().navigate(R.id.action_alarmFragment_to_alarmRepeatWeekFragment)
    }*/

    companion object {
        const val ALARM_REPEAT_POP_BACK = "alarm_repeat_pop_back"
    }
}
