package com.example.mypet.ui.food

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentFoodBinding
import com.example.mypet.domain.food.FoodModel
import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.ui.pet.PetGraphViewModel
import com.example.mypet.ui.pet.list.OnAddPetClickListener
import com.example.mypet.ui.pet.list.OnPetClickListener
import com.example.mypet.ui.pet.list.PetListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodFragment : Fragment(R.layout.fragment_food) {
    private val binding by viewBinding(FragmentFoodBinding::bind)
    private val graphViewModel by navGraphViewModels<PetGraphViewModel>(R.id.navigationPet) { defaultViewModelProviderFactory }
    private val viewModel by viewModels<FoodViewModel>()

    private val onClickAddPetList = object : OnAddPetClickListener {
        override fun onAddPetClick() {
        }
    }

    private val onClickItemPetList = object : OnPetClickListener {
        override fun onPetClick(pet: PetModel) {
        }
    }

    private val petListAdapter = PetListAdapter(onClickItemPetList, onClickAddPetList)

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

        graphViewModel.updatePetList()
        initView()
        startObservePetList()
        startObserveFoods()

        viewModel.updateFood(graphViewModel.activePetId)

        /*        binding.buttonPetDetailFoodAdd.setOnClickListener {
                    navToFoodAlarmSet()
                }*/

    }

    private fun initView() {
        binding.includePetList.recyclerViewPetList.adapter = petListAdapter
        binding.recyclerViewFood.adapter = foodAdapter
    }

    private fun startObservePetList() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                graphViewModel.petList.collectLatest { petModels ->
                    if (petModels.isNotEmpty()) onPetListUpdate(petModels) else onPetListEmpty()
                }
            }
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

    private fun onFoodUpdated(foodModels: List<FoodModel>) {
        foodAdapter.submitList(foodModels)
    }

    private fun onFoodEmpty() {

    }

    private fun onPetListUpdate(petModels: List<PetModel>) {
        val activePetModel = petModels.find { it.isActive } ?: petModels.first()
        graphViewModel.activePetId = activePetModel.id
        petListAdapter.setPetList(petModels)
    }

    private fun onPetListEmpty() {
    }

    private fun navToFoodAlarm(foodModel: FoodModel? = null) {
        graphViewModel.activePetId?.let {
            val directions = FoodFragmentDirections
                .actionFoodFragmentToNavigationFoodAlarm(
                    petMyId = it,
                    petFoodId = foodModel?.foodId ?: 0
                )

            findNavController().navigate(directions)
        }
    }
}