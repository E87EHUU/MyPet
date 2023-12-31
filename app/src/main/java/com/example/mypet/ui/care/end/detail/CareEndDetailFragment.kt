package com.example.mypet.ui.care.end.detail

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareEndDetailBinding
import com.example.mypet.domain.care.CareEndModel
import com.example.mypet.domain.care.end.CareEndTypes
import com.example.mypet.domain.toAppDate
import com.example.mypet.ui.care.CareViewModel
import com.example.mypet.ui.clear
import com.example.mypet.ui.getToolbar
import com.google.android.material.datepicker.MaterialDatePicker

class CareEndDetailFragment : Fragment(R.layout.fragment_care_end_detail) {
    private val binding by viewBinding(FragmentCareEndDetailBinding::bind)
    private val viewModel by navGraphViewModels<CareViewModel>(R.id.navigationPetCare) { defaultViewModelProviderFactory }

    private var isUnlockUI = true

    override fun onPause() {
        super.onPause()

        viewModel.careEndModel?.let { careEndModel ->
            careEndModel.afterTimes =
                try {
                    binding.textInputEditTextCareEndDetailEndAfterTimes.text.toString().toInt()
                } catch (_: Exception) {
                    null
                }
        }
    }

    override fun onStart() {
        super.onStart()
        initToolbar()
    }

    override fun onStop() {
        super.onStop()
        getToolbar()?.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        updateUI()
        initListeners()
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
        }
    }

    private fun initView() {

    }

    private fun updateUI() {
        viewModel.careEndModel?.let { careEndModel ->
            careEndModel.updateUI()
            updateUIAfterDate()
        }
    }

    private fun CareEndModel.updateUI() {
        afterTimes?.let {
            binding.textInputEditTextCareEndDetailEndAfterTimes.setText(it.toString())
        }

        when (typeOrdinal) {
            CareEndTypes.AFTER_TIMES.ordinal -> {
                updateUIAfterTimes()
                binding.radioButtonCareEndDetailEndAfterTimes.isChecked = true
            }

            CareEndTypes.AFTER_TIME_IN_MILLIS.ordinal -> {
                updateUIEndAfterDate()
                binding.radioButtonCareEndDetailEndAfterDate.isChecked = true
            }

            else -> {
                updateUIEndNone()
                binding.radioButtonCareEndDetailEndNone.isChecked = true
            }
        }
    }

    private fun updateUIAfterTimes() {
        with(binding) {
            radioButtonCareEndDetailEndNone.isChecked = false
            radioButtonCareEndDetailEndAfterTimes.isChecked = true
            radioButtonCareEndDetailEndAfterDate.isChecked = false

            textInputEditTextCareEndDetailEndAfterTimes.isEnabled = true
            textInputEditTextCareEndDetailEndAfterDate.isEnabled = false
        }
    }

    private fun updateUIEndAfterDate() {
        with(binding) {
            radioButtonCareEndDetailEndNone.isChecked = false
            radioButtonCareEndDetailEndAfterTimes.isChecked = false
            radioButtonCareEndDetailEndAfterDate.isChecked = true

            textInputEditTextCareEndDetailEndAfterTimes.isEnabled = false
            textInputEditTextCareEndDetailEndAfterDate.isEnabled = true
        }
    }

    private fun updateUIEndNone() {
        with(binding) {
            radioButtonCareEndDetailEndNone.isChecked = true
            radioButtonCareEndDetailEndAfterTimes.isChecked = false
            radioButtonCareEndDetailEndAfterTimes.isChecked = false

            textInputEditTextCareEndDetailEndAfterTimes.isEnabled = false
            textInputEditTextCareEndDetailEndAfterDate.isEnabled = false
        }
    }

    private fun updateUIAfterDate() {
        viewModel.careEndModel?.afterDate?.let {
            binding.textInputEditTextCareEndDetailEndAfterDate.setText(toAppDate(it))
        }
    }

    private fun initListeners() {
        val inputFilterAfterTimes = arrayOf(
            InputFilter { source, _, _, dest, _, _ ->
                if (dest.isEmpty() && source == "0") "1"
                else if (dest.length < 2) source
                else ""
            }
        )

        with(binding) {
            textInputEditTextCareEndDetailEndAfterTimes.filters = inputFilterAfterTimes

            constraintLayoutCareEndDetailEndAfterTimes.setOnClickListener {
                viewModel.careEndModel?.let { careEndModel ->
                    careEndModel.typeOrdinal = CareEndTypes.AFTER_TIMES.ordinal
                    updateUIAfterTimes()
                }
            }

            constraintLayoutCareEndDetailEndAfterDate.setOnClickListener {
                viewModel.careEndModel?.let { careEndModel ->
                    careEndModel.typeOrdinal = CareEndTypes.AFTER_TIME_IN_MILLIS.ordinal
                    updateUIEndAfterDate()
                }
            }

            constraintLayoutCareEndDetailEndNone.setOnClickListener {
                viewModel.careEndModel?.let { careEndModel ->
                    careEndModel.typeOrdinal = CareEndTypes.NONE.ordinal
                    updateUIEndNone()
                }
            }

            textInputEditTextCareEndDetailEndAfterDate.setOnClickListener { showDatePicker() }
        }
    }

    private val datePicker by lazy {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.care_date_picker_title))
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .apply {
                viewModel.careEndModel?.let {
                    setSelection(it.afterDate)
                }
            }
            .build()

        datePicker.addOnDismissListener { isUnlockUI = true }
        datePicker.addOnPositiveButtonClickListener { timeInMillis ->
            viewModel.careEndModel?.let {
                it.afterDate = timeInMillis
                updateUIAfterDate()
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

    private fun saveAndPopBack() {
        findNavController().popBackStack()
    }
}
