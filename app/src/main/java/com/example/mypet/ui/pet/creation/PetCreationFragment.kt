package com.example.mypet.ui.pet.creation

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetCreationBinding
import com.example.mypet.data.local.room.model.LocalPetKindModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetCreationFragment : Fragment(R.layout.fragment_pet_creation) {

    private val binding by viewBinding(FragmentPetCreationBinding::bind)
    private val viewModel by viewModels<PetCreationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObservePetKindAndBreed()
    }

    private fun startObservePetKindAndBreed() {

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.kindList.collectLatest { listPetKindModel ->
                    onKindUpdate(listPetKindModel)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.breedList.collectLatest { breedNameList ->
                    onBreedUpdate(breedNameList)
                }
            }
        }
    }

    private fun onKindUpdate(listKindModel: List<LocalPetKindModel>) {
        val kindSpinner = binding.kindSpinner
        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                listKindModel.map { it.kindName })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        kindSpinner.adapter = adapter

        kindSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = position + 1
                viewModel.getBreedList(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Действие при отсутствии выбора
            }
        }
    }

    private fun onBreedUpdate(listBreedName: List<String>) {
        if (listBreedName.isEmpty()) {
            binding.breedSpinner.visibility = View.GONE
            binding.breedSpinnerTittle.visibility = View.GONE
        } else {
            binding.breedSpinner.visibility = View.VISIBLE
            binding.breedSpinnerTittle.visibility = View.VISIBLE

            val breedSpinner = binding.breedSpinner
            val breedSpinnerAdapter =
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    listBreedName
                )
            breedSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            breedSpinner.adapter = breedSpinnerAdapter
        }
    }
}