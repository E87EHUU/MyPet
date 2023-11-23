package com.example.mypet.ui.food.detail

import android.os.Bundle
import android.view.View
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
import com.example.mypet.ui.is24HourFormat
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
    }

    private fun updateUI() {
        binding.timePickerFoodDetail.apply {
            hour = viewModel.hour
            minute = viewModel.minute
        }

        binding.textInputEditTextFoodDetailTitle.setText(viewModel.title)
//        binding.textInputEditTextFoodDetailRation.setText(viewModel.)

/*        updateUIRingtoneDescription()
        updateUIRepeatDescription()
        updateUIDelayChecker()
        updateUIVibrationChecker()*/
    }

    private fun initListeners() {
        binding.timePickerFoodDetail.setOnTimeChangedListener { _, hourOfDay, minute ->
            viewModel.hour = hourOfDay
            viewModel.minute = minute
        }

        binding.includeFoodDetailRepeat.root.setOnClickListener {

        }
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
}