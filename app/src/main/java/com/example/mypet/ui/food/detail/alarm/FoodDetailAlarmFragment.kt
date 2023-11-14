package com.example.mypet.ui.food.detail.alarm

import android.app.Dialog
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentFoodDetailAlarmBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoodDetailAlarmFragment : BottomSheetDialogFragment(R.layout.fragment_food_detail_alarm) {
    private val binding by viewBinding(FragmentFoodDetailAlarmBinding::bind)
    private val viewModel by navGraphViewModels<FoodDetailAlarmViewModel>(R.id.navigation_food_detail_alarm) { defaultViewModelProviderFactory }
    private val args by navArgs<FoodDetailAlarmFragmentArgs>()

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

        tryUpdateFoodDetailAlarmModel()

        observePopBackStack()

        prepListeners()
    }

    private fun tryUpdateFoodDetailAlarmModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.update(args)?.collectLatest { updateUI() } ?: run { updateUI() }
            }
        }
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

    private fun updateUI() {
        binding.timePickerAlarmSetTime.setIs24HourView(requireContext().is24HourFormat)
        binding.timePickerAlarmSetTime.apply {
            hour = viewModel.hour
            minute = viewModel.minute
        }

        binding.textInputEditTextAlarmSetDescription.setText(viewModel.name)

        updateUIRingtoneDescription()
        updateUIRepeatDescription()
        updateUIDelayChecker()
        updateUIVibrationChecker()
    }

    private fun prepListeners() {
        binding.buttonAlarmSetClose.setOnClickListener {
            dismiss()
        }

        binding.buttonAlarmSetOk.setOnClickListener {
            saveAndPopBack()
        }

        binding.timePickerAlarmSetTime.setOnTimeChangedListener { _, hourOfDay, minute ->
            viewModel.hour = hourOfDay
            viewModel.minute = minute
        }

        binding.LayerAlarmSetVibration.setOnClickListener {
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
        }
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
        findNavController().navigate(R.id.action_alarmSet_to_alarmRepeat)
    }

    companion object {
        const val ALARM_REPEAT_POP_BACK = "alarm_repeat_pop_back"
    }
}