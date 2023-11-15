package com.example.mypet.ui.food

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentFoodBinding
import com.example.mypet.domain.food.FoodModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodFragment : Fragment(R.layout.fragment_food) {
    private val binding by viewBinding(FragmentFoodBinding::bind)
    private val viewModel by viewModels<FoodViewModel>()

    private val foodAdapterCallback =
        object : FoodAdapterCallback {
            override fun onItemClick(foodModel: FoodModel) {
                navToFoodAlarm(foodModel)
            }

            override fun onSwitchActive(foodModel: FoodModel) {
                viewModel.switchAlarmState(foodModel)
            }
        }

    private val foodAdapter = FoodAdapter(foodAdapterCallback)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        startObserveFoods()

        viewModel.updateFood(1)

        /*        binding.buttonPetDetailFoodAdd.setOnClickListener {
                    navToFoodAlarmSet()
                }*/

    }

    private fun initView() {
        binding.recyclerViewFood.adapter = foodAdapter
    }

    private fun startObserveFoods() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.food.collectLatest {
                    if (it.isEmpty()) onFoodEmpty()
                    else onFoodUpdated(it)
                }
            }
        }
    }

    private fun onFoodUpdated(foodModels: List<FoodModel>) {
        foodAdapter.submitList(foodModels)
    }

    private fun onFoodEmpty() {

    }

    private fun navToFoodAlarm(foodModel: FoodModel? = null) {
        viewModel.petId?.let {
            val directions = FoodFragmentDirections
                .actionFoodToFoodAlarm(
                    petMyId = it,
                    petFoodId = foodModel?.foodId ?: 0
                )

            findNavController().navigate(directions)
        }
    }
}