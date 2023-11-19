package com.example.mypet.ui.pet

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetBinding
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.domain.pet.food.PetFoodModel
import com.example.mypet.ui.getPetIcon
import com.example.mypet.ui.getPetName
import com.example.mypet.ui.pet.care.PetCareAdapter
import com.example.mypet.ui.pet.care.PetCareAdapterCallback
import com.example.mypet.ui.pet.food.PetFoodAdapter
import com.example.mypet.ui.pet.food.PetFoodAdapterCallback
import com.example.mypet.ui.pet.list.OnAddPetClickListener
import com.example.mypet.ui.pet.list.OnPetClickListener
import com.example.mypet.ui.pet.list.PetListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetFragment : Fragment(R.layout.fragment_pet), OnAddPetClickListener,
    OnPetClickListener {
    private val binding by viewBinding(FragmentPetBinding::bind)
    private val viewModel by viewModels<PetViewModel>()

    private val petListAdapter: PetListAdapter by lazy {
        PetListAdapter(this, this)
    }

    private val petFoodAdapterCallback =
        object : PetFoodAdapterCallback {
            override fun onItemClick(petFoodModel: PetFoodModel) {
                TODO("Not yet implemented")
            }

        }
    private val petFoodAdapter = PetFoodAdapter(petFoodAdapterCallback)

    private val petCareAdapterCallback =
        object : PetCareAdapterCallback {
            override fun onItemClick(petCareModel: PetCareModel) {
                TODO("Not yet implemented")
            }
        }
    private val petCareAdapter = PetCareAdapter(petCareAdapterCallback)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updatePetList()
        initView()
        startObservePetList()
        startObservePetFoodList()
        startObservePetCareList()
    }

    private fun startObservePetList() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.petList.collectLatest { petModels ->
                    if (petModels.isNotEmpty()) onNotEmptyPetModels(petModels)
                    else onEmptyPetModels()
                }
            }
        }
    }

    private fun onNotEmptyPetModels(petModels: List<PetModel>) {
        petListAdapter.setPetList(petModels)
        val activePetModel = petModels.find { it.isActive } ?: petModels.first()
        viewModel.activePetId = activePetModel.id
        onPetUpdate(activePetModel)
    }

    private fun onEmptyPetModels() {
        binding.textViewPetEmpty.isVisible = true
    }

    private fun startObservePetFoodList() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.petFoodList.collectLatest { petFoodModels ->
                    if (petFoodModels.isNotEmpty()) onNotEmptyPetFoodModels(petFoodModels)
                    else onEmptyPetFoodModels()
                }
            }
        }
    }

    private fun onNotEmptyPetFoodModels(petFoodModels: List<PetFoodModel>) {
        petFoodAdapter.submitList(petFoodModels)
    }

    private fun onEmptyPetFoodModels() {

    }

    private fun startObservePetCareList() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.petCareList.collectLatest { petCareModels ->
                    if (petCareModels.isNotEmpty()) onNotEmptyPetCareModels(petCareModels)
                    else onEmptyPetCareModels()
                }
            }
        }
    }

    private fun onNotEmptyPetCareModels(petCareModels: List<PetCareModel>) {
        petCareAdapter.submitList(petCareModels)
    }

    private fun onEmptyPetCareModels() {

    }

    private fun initView() {
        binding.recyclerViewPetList.adapter = petListAdapter
        binding.recyclerViewPetFoodList.adapter = petFoodAdapter
        binding.recyclerViewPetCareList.adapter = petCareAdapter
    }

    private fun onPetUpdate(petModel: PetModel) {
        if (petModel.avatarUri != null)
            binding.imageViewPetAvatarIcon.setImageURI(petModel.avatarUri)
        else
            binding.imageViewPetAvatarIcon
                .setImageResource(getPetIcon(petModel.kindOrdinal, petModel.breedOrdinal))

        binding.textViewPetName.text = petModel.name
        binding.textViewPetBreedName.text =
            getString(getPetName(petModel.kindOrdinal, petModel.breedOrdinal))

        binding.textViewPetEmpty.isVisible = false

        petModel.age?.let {
            binding.textViewPetAgeText.text = petModel.age
            binding.materialCardViewPetAge.isVisible = true
        } ?: run {
            binding.materialCardViewPetAge.isVisible = false
        }

        petModel.weight?.let {
            binding.textViewPetWeightText.text = petModel.weight
            binding.materialCardViewPetWeight.isVisible = true
        } ?: run {
            binding.materialCardViewPetWeight.isVisible = false
        }
    }

    override fun onAddPetClick() {
        // Переход на фрагмент создания нового питомца"
    }

    override fun onPetClick(pet: PetModel) {
        onPetUpdate(pet)
    }
}