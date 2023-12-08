package com.example.mypet.ui.care.repeat.detail

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareRepeatDetailBinding
import com.example.mypet.domain.care.repeat.CareRepeatEndTypes
import com.example.mypet.domain.care.repeat.CareRepeatInterval
import com.example.mypet.ui.care.CareViewModel
import com.example.mypet.ui.getToolbar
import com.example.mypet.ui.toAppDate
import com.google.android.material.datepicker.MaterialDatePicker

class CareRepeatDetailFragment : Fragment(R.layout.fragment_care_repeat_detail) {
    private val binding by viewBinding(FragmentCareRepeatDetailBinding::bind)
    private val viewModel by navGraphViewModels<CareViewModel>(R.id.navigationPetCare) { defaultViewModelProviderFactory }

    private var isUnlockUI = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        updateUI()
        initListeners()
    }

    override fun onPause() {
        super.onPause()
        viewModel.careRepeatModel?.let { careRepeatModel ->
            careRepeatModel.intervalTimes =
                binding.textInputEditTextCareRepeatIntervalTimes.text.toString()

            careRepeatModel.endAfterTimes =
                binding.textInputEditTextCareRepeatEndAfterTimes.text.toString()
        }
    }

    private fun initView() {
        getToolbar()?.let { toolbar ->
            toolbar.title = null
            toolbar.menu.clear()
            toolbar.inflateMenu(R.menu.toolbar_save)
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.toolbarSave -> {
                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun updateUI() {
        viewModel.careRepeatModel?.let { careRepeatModel ->
            binding.textInputEditTextCareRepeatIntervalTimes.setText(careRepeatModel.intervalTimes)
            updateUIInterval()
            updateUIWeekChips()
            updateUIEnd()
            binding.textInputEditTextCareRepeatEndAfterTimes.setText(careRepeatModel.endAfterTimes)
            updateUIEndAfterDateText()
        }
    }

    private fun updateUIEnd() {
        when (viewModel.careRepeatModel?.endTypeOrdinal) {
            CareRepeatEndTypes.AFTER_TIMES.ordinal -> {
                updateUIEndAfterTimes()
                binding.radioButtonCareRepeatEndAfterTimes.isChecked = true
            }

            CareRepeatEndTypes.AFTER_DATE.ordinal -> {
                updateUIEndAfterDate()
                binding.radioButtonCareRepeatEndAfterDate.isChecked = true
            }

            else -> {
                updateUIEndNone()
                binding.radioButtonCareRepeatEndNone.isChecked = true
            }
        }
    }

    private fun updateUIEndAfterTimes() {
        changeStateContentRadioEndAfterDate()
        changeStateContentRadioEndAfterTimes(isEnabled = true)
    }

    private fun updateUIEndAfterDate() {
        changeStateContentRadioEndAfterTimes()
        changeStateContentRadioEndAfterDate(isEnabled = true)
    }

    private fun updateUIEndNone() {
        changeStateContentRadioEndAfterTimes()
        changeStateContentRadioEndAfterDate()
    }

    private fun changeStateContentRadioEndAfterTimes(isEnabled: Boolean = false) {
        binding.textInputLayoutsCareRepeatEndTimes.isEnabled = isEnabled
    }

    private fun changeStateContentRadioEndAfterDate(isEnabled: Boolean = false) {
        binding.textInputEditTextCareRepeatEndDate.isEnabled = isEnabled
    }

    private fun updateUIInterval() {
        when (viewModel.careRepeatModel?.intervalOrdinal) {
            CareRepeatInterval.WEEK.ordinal -> {
                updateUIIntervalText()
                updateUIWeekDetail(isVisible = true)
            }

            CareRepeatInterval.MONTH.ordinal -> {
                updateUIIntervalText()
                updateUIWeekDetail()
            }

            CareRepeatInterval.YEAR.ordinal -> {
                updateUIIntervalText()
                updateUIWeekDetail()
            }

            else -> {
                updateUIIntervalText()
                updateUIWeekDetail()
            }
        }
    }

    private fun updateUIIntervalText() {
        viewModel.careRepeatModel?.intervalOrdinal?.let {
            binding.textInputEditTextCareRepeatInterval.setText(
                CareRepeatInterval.values()[it].titleResId
            )
        }
    }

    private fun updateUIWeekDetail(isVisible: Boolean = false) {
        binding.chipGroupCareRepeatWeek.isVisible = isVisible
    }

    private fun updateUIEndAfterDateText() {
        viewModel.careRepeatModel?.endAfterDate?.let {
            binding.textInputEditTextCareRepeatEndDate.setText(toAppDate(it))
        }
    }

    private fun updateUIWeekChips() {
        viewModel.careRepeatModel?.let { careRepeatModel ->
            binding.chipCareRepeatMonday.isChecked = careRepeatModel.isMonday
            binding.chipCareRepeatTuesday.isChecked = careRepeatModel.isTuesday
            binding.chipCareRepeatWednesday.isChecked = careRepeatModel.isWednesday
            binding.chipCareRepeatThursday.isChecked = careRepeatModel.isThursday
            binding.chipCareRepeatFriday.isChecked = careRepeatModel.isFriday
            binding.chipCareRepeatSaturday.isChecked = careRepeatModel.isSaturday
            binding.chipCareRepeatSunday.isChecked = careRepeatModel.isSunday
        }
    }

    private fun initListeners() {
        val inputFilterIntervalTimes = arrayOf(
            InputFilter { source, _, _, dest, _, _ ->
                if (dest.isEmpty() && source == "0") "1"
                else if (dest.length < 2) source
                else ""
            }
        )

        val inputFilterEndAfterTimes = arrayOf(
            InputFilter { source, _, _, dest, _, _ ->
                if (dest.isEmpty() && source == "0") "1"
                else if (dest.length < 2) source
                else ""
            }
        )

        binding.textInputEditTextCareRepeatIntervalTimes.filters = inputFilterIntervalTimes
        binding.textInputEditTextCareRepeatEndAfterTimes.filters = inputFilterEndAfterTimes

        binding.chipGroupCareRepeatWeek.setOnCheckedStateChangeListener { _, checkedIds ->
            viewModel.careRepeatModel?.let { careRepeatModel ->
                careRepeatModel.isMonday = checkedIds.contains(R.id.chipCareRepeatMonday)
                careRepeatModel.isTuesday = checkedIds.contains(R.id.chipCareRepeatTuesday)
                careRepeatModel.isWednesday = checkedIds.contains(R.id.chipCareRepeatWednesday)
                careRepeatModel.isThursday = checkedIds.contains(R.id.chipCareRepeatThursday)
                careRepeatModel.isFriday = checkedIds.contains(R.id.chipCareRepeatFriday)
                careRepeatModel.isSaturday = checkedIds.contains(R.id.chipCareRepeatSaturday)
                careRepeatModel.isSunday = checkedIds.contains(R.id.chipCareRepeatSunday)
            }
        }

        binding.radioGroupCareRepeatEnd.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonCareRepeatEndAfterTimes -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.endTypeOrdinal = CareRepeatEndTypes.AFTER_TIMES.ordinal
                        updateUIEndAfterTimes()
                    }
                }

                R.id.radioButtonCareRepeatEndAfterDate -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.endTypeOrdinal = CareRepeatEndTypes.AFTER_DATE.ordinal
                        updateUIEndAfterDate()
                    }
                }

                else -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.endTypeOrdinal = CareRepeatEndTypes.NONE.ordinal
                        updateUIEndNone()
                    }
                }
            }
        }

        binding.textInputEditTextCareRepeatInterval.setOnClickListener { showPopUpRepeatTimes() }
        binding.textInputEditTextCareRepeatEndDate.setOnClickListener { showDatePicker() }
    }


    private fun showPopUpRepeatTimes() {
        val popupMenu = PopupMenu(context, binding.textInputEditTextCareRepeatInterval)
        popupMenu.menuInflater.inflate(R.menu.popup_care_repeat_times, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.care_repeat_times_day -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.intervalOrdinal = CareRepeatInterval.DAY.ordinal
                        updateUIInterval()
                    }
                    true
                }

                R.id.care_repeat_times_week -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.intervalOrdinal = CareRepeatInterval.WEEK.ordinal
                        updateUIInterval()
                    }
                    true
                }

                R.id.care_repeat_times_month -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.intervalOrdinal = CareRepeatInterval.MONTH.ordinal
                        updateUIInterval()
                    }

                    true
                }

                R.id.care_repeat_times_year -> {
                    viewModel.careRepeatModel?.let { careRepeatModel ->
                        careRepeatModel.intervalOrdinal = CareRepeatInterval.YEAR.ordinal
                        updateUIInterval()
                    }
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    private val datePicker by lazy {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.date_picker_title))
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .apply {
                viewModel.careRepeatModel?.let {
                    setSelection(it.endAfterDate)
                }
            }
            .build()

        datePicker.addOnDismissListener { isUnlockUI = true }
        datePicker.addOnPositiveButtonClickListener { timeInMillis ->
            viewModel.careRepeatModel?.let {
                it.endAfterDate = timeInMillis
                updateUIEndAfterDateText()
            }
        }
        datePicker
    }

    private fun showDatePicker() {
        if (isUnlockUI)
            activity?.supportFragmentManager?.let { fragmentManager ->
                isUnlockUI = false
                datePicker.show(fragmentManager, datePicker.toString())
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
}
