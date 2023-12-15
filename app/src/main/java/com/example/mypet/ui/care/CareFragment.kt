package com.example.mypet.ui.care

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentCareBinding
import com.example.mypet.domain.care.alarm.CareAlarmDetailMainModel
import com.example.mypet.ui.care.alarm.CareAlarmCallback
import com.example.mypet.ui.care.main.CareMainCallback
import com.example.mypet.ui.care.repeat.CareRepeatCallback
import com.example.mypet.ui.care.start.CareStartCallback
import com.example.mypet.ui.getToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CareFragment : Fragment(R.layout.fragment_care),
    CareMainCallback, CareStartCallback, CareRepeatCallback, CareAlarmCallback {
    private val binding by viewBinding(FragmentCareBinding::bind)

    private val viewModel by navGraphViewModels<CareViewModel>(R.id.navigationPetCare) { defaultViewModelProviderFactory }
    private val args by navArgs<CareFragmentArgs>()

    private val adapter = CareAdapter(this, this, this, this)
    private var isUnlockUI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateCare(args.petId, args.careId, args.careTypeOrdinal)
    }

    override fun onResume() {
        super.onResume()
        viewModel.careAlarmDetailMainModel = null
        initToolbar()
    }

    override fun onStop() {
        super.onStop()
        getToolbar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initView()
        initObserveCareAdapterModels()
    }

    private fun initView() {
        binding.root.adapter = adapter
    }

    private fun initToolbar() {
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
    }

    private fun initObserveCareAdapterModels() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.careModels.collectLatest { mutableList ->
                    adapter.submitList(mutableList)
                    viewModel.careMainModel?.careType?.titleResId?.let {
                        getToolbar()?.title = getString(it)
                    }
                }
            }
        }
    }

    private val datePicker by lazy {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.care_date_picker_title))
            .setInputMode(INPUT_MODE_CALENDAR)
            .apply {
                viewModel.careStartModel?.timeInMillis?.let {
                    setSelection(it)
                }
            }
            .build()

        datePicker.addOnDismissListener { isUnlockUI = true }
        datePicker.addOnPositiveButtonClickListener {
            viewModel.careStartModel?.timeInMillis = it
            adapter.startViewHolder?.updateDate()
        }
        datePicker
    }

    private fun navToRepeat() {
        findNavController().navigate(R.id.action_careFragment_to_careRepeatDetailFragment)
    }

    private fun navToAlarmDetail() {
        findNavController().navigate(R.id.action_careFragment_to_alarmDetailFragment)
    }

    override fun onClickAlarm(careAlarmDetailMainModel: CareAlarmDetailMainModel?) {
        viewModel.careAlarmDetailMainModel = careAlarmDetailMainModel
        navToAlarmDetail()
    }

    override fun onClickDelete(careAlarmDetailMainModel: CareAlarmDetailMainModel) {
        viewModel.alarmDelete(careAlarmDetailMainModel)
    }

    override fun onClickRepeat() {
        navToRepeat()
    }

    override fun showDatePicker() {
        if (isUnlockUI)
            activity?.supportFragmentManager?.let { fragmentManager ->
                isUnlockUI = false
                datePicker.show(fragmentManager, datePicker.toString())
            }
    }

    private fun saveAndPopBack() {
        viewModel.saveCare()
        findNavController().popBackStack()
    }
}