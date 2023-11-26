package com.example.mypet.ui.food.detail

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentFoodDetailBinding
import com.example.mypet.domain.alarm.AlarmAlertType
import com.example.mypet.domain.alarm.AlarmModel
import com.example.mypet.ui.getToolbar
import com.example.mypet.ui.is24HourFormat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoodDetailFragment : Fragment(R.layout.fragment_food_detail) {
    private val binding by viewBinding(FragmentFoodDetailBinding::bind)
    private val viewModel by viewModels<FoodDetailViewModel>()
    private val args by navArgs<FoodDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        tryUpdateFoodDetailModel()
        initListeners()
    }

    private fun tryUpdateFoodDetailModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.update(args.petFoodId)?.collectLatest { updateUI() } ?: run { updateUI() }
            }
        }
    }

    private fun initView() {
        binding.timePickerFoodDetail.setIs24HourView(requireContext().is24HourFormat)

        getToolbar()?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.toolbarSave -> {
                    trySave()
                    true
                }

                else -> false
            }
        }
    }

    private fun updateUI() {
        binding.timePickerFoodDetail.apply {
            hour = viewModel.hour
            minute = viewModel.minute
        }

        binding.textInputEditTextFoodDetailTitle.setText(viewModel.title)
        binding.textInputEditTextFoodDetailRation.setText(viewModel.ration)
    }

    private fun initListeners() {
        binding.timePickerFoodDetail.setOnTimeChangedListener { _, hourOfDay, minute ->
            viewModel.hour = hourOfDay
            viewModel.minute = minute
        }

        binding.textInputEditTextFoodDetailTitle.doAfterTextChanged {
            viewModel.title = it.toString()
        }

        binding.textInputEditTextFoodDetailRation.doAfterTextChanged {
            viewModel.ration = it.toString()
        }

        binding.includeFoodDetailRepeat.root.setOnClickListener {
            navToAlarmRepeat(viewModel.alarmModel)
        }

        binding.includeFoodDetailAlert.chipSnippetAlertAlarm.setOnClickListener {
        }

        binding.includeFoodDetailAlert.chipGroupSnippetAlert.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isEmpty())
                viewModel.alarmAlertTypeOrdinal = AlarmAlertType.NONE.ordinal
            else
                when (checkedIds.first()) {
                    R.id.chipSnippetAlertAlarm -> {
                        if (!Settings.canDrawOverlays(requireContext())) showAlarmDialog()
                        else viewModel.alarmAlertTypeOrdinal = AlarmAlertType.ALL.ordinal
                    }

                    else -> {
                        viewModel.alarmAlertTypeOrdinal = AlarmAlertType.ONLY_NOTIFICATION.ordinal
                    }
                }
        }
    }

    private fun trySave() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.save().collectLatest {
                launch(Dispatchers.Main) {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun navToAlarmRepeat(alarmModel: AlarmModel?) {
        alarmModel?.let {
            val directions =
                FoodDetailFragmentDirections.actionFoodDetailFragmentToAlarmFragment(alarmModel)
            findNavController().navigate(directions)
        }
    }

    private fun showAlarmDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Разрешение \"Поверх всех окон\"")
            .setMessage("Для работы будильника необходимо предоставить приложению \"Мой питомец\" разрешение")
            .setNegativeButton("Отказать") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Предоставить") { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivityForResult(intent, 0)
            }
            .create()
            .show()
    }
}