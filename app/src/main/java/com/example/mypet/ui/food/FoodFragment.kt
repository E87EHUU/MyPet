package com.example.mypet.ui.food

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
import com.example.mypet.app.databinding.FragmentFoodBinding
import com.example.mypet.domain.food.CareAlarmModel
import com.example.mypet.domain.food.CareModel
import com.example.mypet.ui.getFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodFragment : Fragment(R.layout.fragment_food) {
    private val binding by viewBinding(FragmentFoodBinding::bind)
    private val viewModel by viewModels<FoodViewModel>()
    private val args by navArgs<FoodFragmentArgs>()

    private val careFoodCallback =
        object : CareFoodCallback {
            override fun onItemClick() {
                TODO("Not yet implemented")
            }
        }

    private val careRepeatCallback =
        object : CareRepeatCallback {
            override fun onItemClick() {
                navToRepeat()
            }
        }

    private val careAlarmCallback =
        object : CareAlarmCallback {
            override fun onItemClick(careAlarmModel: CareAlarmModel) {
                navToAlarmDetail(careAlarmModel)
            }

            override fun onSwitchActive(careAlarmModel: CareAlarmModel) {
                viewModel.switchAlarmState(careAlarmModel)
            }

        }

    private val foodAdapter = CareAdapter(careFoodCallback, careRepeatCallback, careAlarmCallback)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListeners()
        startObserveFoods()

        viewModel.updateFood(args.petMyId)
    }

    private fun initView() {
        binding.recyclerViewFood.adapter = foodAdapter
    }

    private fun initListeners() {
        getFloatingActionButton()?.setOnClickListener {
            navToFoodDetail()
        }
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

    private fun onFoodUpdated(foodModels: List<CareModel>) {
        foodAdapter.submitList(foodModels)
    }

    private fun onFoodEmpty() {

    }

    private fun navToRepeat() {
        val directions = FoodFragmentDirections.actionFoodFragmentToRepeatFragment()
        findNavController().navigate(directions)
    }

    private fun navToAlarmDetail(careAlarmModel: CareAlarmModel) {
        careAlarmModel.alarmId?.let {
            val directions =
                FoodFragmentDirections.actionFoodFragmentToAlarmDetailFragment(careAlarmModel.alarmId)
            findNavController().navigate(directions)
        }
    }


    private fun navToFoodDetail(foodModel: CareModel? = null) {
        /*        viewModel.petMyId?.let {
                    val directions = FoodFragmentDirections
                        .actionFoodFragmentToFoodDetailFragment(
                            petMyId = it,
                            petFoodId = foodModel?.foodId ?: 0
                        )

                    findNavController().navigate(directions)
                }*/
    }
}