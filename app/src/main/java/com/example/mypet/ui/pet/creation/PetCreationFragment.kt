package com.example.mypet.ui.pet.creation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetCreationBinding
import com.example.mypet.data.local.room.model.LocalPetKindModel
import com.example.mypet.domain.pet.kind.getKindNameResId
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class PetCreationFragment : Fragment(R.layout.fragment_pet_creation) {

    private val binding by viewBinding(FragmentPetCreationBinding::bind)
    private val viewModel by viewModels<PetCreationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObservePetKindAndBreed()
        saveNewPet()
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
                listKindModel.map { getString(getKindNameResId(it.id - 1)) })
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
                val chosenKindName = parent?.getItemAtPosition(position).toString()
                viewModel.kind = position + 1
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

            breedSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val chosenBreedName = parent?.getItemAtPosition(position).toString()
                    viewModel.breed = position + 1
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Действие при отсутствии выбора
                }
            }
        }
    }

    private fun chooseDate() {
        val selectedDateTextView = binding.date
        val selectDateButton = binding.selectedDate

        val calendar = Calendar.getInstance()
        selectDateButton.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    calendar.set(selectedYear, selectedMonth, selectedDay)
                    val selectedDate =
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
                    selectedDateTextView.text = selectedDate
                    viewModel.dateOfBirth = selectedDateTextView.text.toString()
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }

    private fun saveNewPet() {
        chooseDate()
        binding.saveNewPet.setOnClickListener {
            viewModel.name = binding.newPetName.text.toString()
            viewModel.weight = binding.weightNewPet.text.toString().toInt()
            with(viewModel) {
                if (name.isEmpty() || kind == 0 || breed == 0 || dateOfBirth.isEmpty() || weight == 0) {
                    Toast.makeText(context, "Заполните все поля", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.addNewPetToDb()
                    findNavController().popBackStack()
                    Toast.makeText(
                        context,
                        "$name, $kind, $breed, $dateOfBirth, $weight",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }
}