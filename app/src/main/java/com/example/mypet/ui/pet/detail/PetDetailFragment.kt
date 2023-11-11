package com.example.mypet.ui.pet.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetDetailBinding
import com.example.mypet.domain.pet.detail.PetFoodModel
import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.ui.pet.detail.food.PetDetailFoodAdapter
import com.example.mypet.ui.pet.detail.food.PetDetailFoodAdapterCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetDetailFragment : Fragment(R.layout.fragment_pet_detail) {
    private val binding by viewBinding(FragmentPetDetailBinding::bind)
    private val viewModel by viewModels<PetDetailViewModel>()

    private val foodAdapterCallback =
        object : PetDetailFoodAdapterCallback {
            override fun onItemClick(petFoodModel: PetFoodModel) {
                navToFoodAlarmSet(petFoodModel)
            }

            override fun onSwitchActive(petFoodModel: PetFoodModel) {
                viewModel.switchPetFoodAlarmState(petFoodModel)
            }
        }
    private val foodAdapter = PetDetailFoodAdapter(foodAdapterCallback)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObservePetDetail()
        viewModel.updatePetModel()

        binding.buttonPetDetailFoodAdd.setOnClickListener {
            navToFoodAlarmSet()
        }

        binding.recyclerViewPetDetailFood.adapter = foodAdapter

        //viewModel.setAlarm()
    }

    private fun startObservePetDetail() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.petModel.collectLatest { petModel ->
                    petModel
                        ?.let {
                            onPetUpdate(it)
                            foodAdapter.submitList(it.foods)
                        }
                        ?: run { onPetEmpty() }
                }
            }
        }
    }

    private fun onPetEmpty() {
        binding.groupPetDetailAge.isVisible = false
        binding.groupPetDetailWeight.isVisible = false
        binding.groupPetDetailOther.isVisible = false
        binding.textViewPetDetailEmpty.isVisible = true
    }

    private fun onPetUpdate(petModel: PetModel) {
        binding.imageViewPetDetailAvatar.setImageURI(petModel.avatar)
        binding.textViewPetDetailName.text = petModel.name
        binding.textViewPetDetailBreedName.text = petModel.breedName

        binding.textViewPetDetailEmpty.isVisible = false
        binding.groupPetDetailOther.isVisible = true

        petModel.age?.let {
            binding.textViewPetDetailAge.text = petModel.age
            binding.groupPetDetailAge.isVisible = true
        } ?: run {
            binding.groupPetDetailAge.isVisible = false
        }

        petModel.weight?.let {
            binding.textViewPetDetailWeight.text = petModel.weight
            binding.groupPetDetailWeight.isVisible = true
        } ?: run {
            binding.groupPetDetailWeight.isVisible = false
        }
    }

    private fun navToFoodAlarmSet(petFoodModel: PetFoodModel? = null) {
        val directions = PetDetailFragmentDirections
            .actionPetDetailToNavigationAlarm(
                petMyId = viewModel.petMyId,
                petFoodId = petFoodModel?.id ?: 0
            )

        findNavController().navigate(directions)
    }
}