package com.example.mypet.ui.pet

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
import com.example.mypet.app.databinding.FragmentPetBinding
import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.domain.pet.kind.getKindIconResId
import com.example.mypet.domain.pet.kind.getKindNameResId
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        startObservePetList()
    }

    private fun startObservePetList() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.petList.collectLatest { petModels ->
                    if (petModels.isNotEmpty()) {
                        petListAdapter.setPetList(petModels)
                        val activePetModel = petModels.find { it.isActive } ?: petModels.first()
                        viewModel.activePetId = activePetModel.id
                        onPetUpdate(activePetModel)
                    } else onPetEmpty()
                }
            }
        }
    }

    private fun initView() {
        binding.recyclerViewPetList.adapter = petListAdapter
    }

    private fun onPetEmpty() {
        binding.textViewPetDetailEmpty.isVisible = true
    }

    private fun onPetUpdate(petModel: PetModel) {
        setImageAvatar(petModel)

        binding.textViewPetDetailName.text = petModel.name
        binding.textViewPetDetailBreedName.text = getString(getKindNameResId(petModel.kindOrdinal))

        binding.textViewPetDetailEmpty.isVisible = false

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

    private fun setImageAvatar(petModel: PetModel) {
        if (petModel.avatarUri != null)
            binding.imageViewPetDetailAvatar.setImageURI(petModel.avatarUri)
        else if (petModel.breedId != null) {
        } else binding.imageViewPetDetailAvatar.setImageResource(getKindIconResId(petModel.kindOrdinal))
    }

    override fun onAddPetClick() {
        findNavController().navigate(R.id.petCreationFragment)
    }

    override fun onPetClick(pet: PetModel) {
        onPetUpdate(pet)
    }
}