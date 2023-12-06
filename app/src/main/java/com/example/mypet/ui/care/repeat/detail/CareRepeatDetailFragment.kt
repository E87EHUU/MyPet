package com.example.mypet.ui.care.repeat.detail

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.core.view.removeItemAt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareRepeatDetailBinding
import com.example.mypet.domain.care.repeat.CareRepeatInterval
import com.example.mypet.ui.toAppDate
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CareRepeatDetailFragment : Fragment(R.layout.fragment_care_repeat_detail) {
    private val binding by viewBinding(FragmentCareRepeatDetailBinding::bind)
    private val viewModel by viewModels<CareRepeatDetailViewModel>()
    private var isUnlockUI = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListeners()
    }

    private fun initView() {
        changeStateContentRadioEndIn()
        changeStateContentRadioEndAfter()
    }

    private fun initListeners() {
        val inputFilterTimes = arrayOf(
            InputFilter { source, _, _, dest, _, _ ->
                if (dest.toString().length < 2) source
                else ""
            }
        )

        binding.textInputEditTextCareRepeatIntervalTimes.filters = inputFilterTimes
        binding.textInputEditTextCareRepeatEndTimes.filters = inputFilterTimes

        binding.chipGroupCareRepeatWeek.setOnCheckedStateChangeListener { group, checkedIds ->

        }

        binding.radioGroupCareRepeatEnd.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButtonCareRepeatEndAfter -> {
                    changeStateContentRadioEndIn()
                    changeStateContentRadioEndAfter(true)
                }

                R.id.radioButtonCareRepeatEndIn -> {
                    changeStateContentRadioEndAfter()
                    changeStateContentRadioEndIn(true)
                }

                else -> {
                    changeStateContentRadioEndAfter()
                    changeStateContentRadioEndIn()
                }
            }
        }

        binding.textInputEditTextCareRepeatInterval.setOnClickListener { showPopUpRepeatTimes() }
        binding.textInputEditTextCareRepeatEndDate.setOnClickListener { showDatePicker() }
    }

    private fun changeStateContentRadioEndAfter(state: Boolean = false) {
        binding.textInputLayoutsCareRepeatEndTimes.isEnabled = state
        binding.textInputLayoutsCareRepeatEndTimesChooser.isEnabled = state
    }

    private fun changeStateContentRadioEndIn(state: Boolean = false) {
        binding.textInputEditTextCareRepeatEndDate.isEnabled = state
    }

    private fun showPopUpRepeatTimes() {
        val popupMenu = PopupMenu(context, binding.textInputEditTextCareRepeatInterval)
        popupMenu.menuInflater.inflate(R.menu.popup_care_repeat_times, popupMenu.menu)
        popupMenu.menu.removeItemAt(viewModel.repeatIntervalOrdinal)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.care_repeat_times_day -> {
                    viewModel.repeatIntervalOrdinal = CareRepeatInterval.DAY.ordinal
                    updateInterval()
                    hideWeekDetail()
                    true
                }

                R.id.care_repeat_times_week -> {
                    viewModel.repeatIntervalOrdinal = CareRepeatInterval.WEEK.ordinal
                    updateInterval()
                    showWeekDetail()
                    true
                }

                R.id.care_repeat_times_month -> {
                    viewModel.repeatIntervalOrdinal = CareRepeatInterval.MONTH.ordinal
                    updateInterval()
                    hideWeekDetail()

                    true
                }

                R.id.care_repeat_times_year -> {
                    viewModel.repeatIntervalOrdinal = CareRepeatInterval.YEAR.ordinal
                    updateInterval()
                    hideWeekDetail()

                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    private fun showWeekDetail() {
        if (!binding.chipGroupCareRepeatWeek.isVisible)
            binding.chipGroupCareRepeatWeek.isVisible = true
    }

    private fun hideWeekDetail() {
        if (binding.chipGroupCareRepeatWeek.isVisible)
            binding.chipGroupCareRepeatWeek.isVisible = false
    }

    private fun updateEndDate() {
        binding.textInputEditTextCareRepeatEndDate.setText(toAppDate(viewModel.timeInMillis))
    }

    private fun updateInterval() {
        binding.textInputEditTextCareRepeatInterval.setText(
            CareRepeatInterval.values()[viewModel.repeatIntervalOrdinal].titleResId
        )
    }

    private val datePicker by lazy {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(viewModel.timeInMillis)
            .setTitleText(getString(R.string.date_picker_title))
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()

        datePicker.addOnDismissListener { isUnlockUI = true }
        datePicker.addOnPositiveButtonClickListener {
            viewModel.timeInMillis = it
            updateEndDate()
        }
        datePicker
    }

    private fun showDatePicker() {
        println("!!!")
        if (isUnlockUI)
            activity?.supportFragmentManager?.let { fragmentManager ->
                isUnlockUI = false
                datePicker.show(fragmentManager, datePicker.toString())
            }
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
