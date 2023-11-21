package com.example.mypet.ui.pet.creation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetCreationBinding
import com.example.mypet.domain.pet.kind.PetKind
import com.example.mypet.ui.getPetBreedList
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class PetCreationFragment : Fragment(R.layout.fragment_pet_creation) {

    private val binding by viewBinding(FragmentPetCreationBinding::bind)
    private val viewModel by viewModels<PetCreationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onKindUpdate()
        saveNewPet()
    }

    private fun onKindUpdate() {
        val kindSpinner = binding.kindSpinner
        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                PetKind.values().map { getString(it.nameResId) }
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        kindSpinner.adapter = adapter

        kindSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onBreedUpdate(position)
                viewModel.kind = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Действие при отсутствии выбора
            }
        }
    }

    private fun onBreedUpdate(kindId: Int) {
        if (getPetBreedList(kindId) == null) {
            binding.breedSpinner.visibility = View.GONE
            binding.breedSpinnerTittle.visibility = View.GONE
        } else {
            binding.breedSpinner.visibility = View.VISIBLE
            binding.breedSpinnerTittle.visibility = View.VISIBLE

            val breedSpinner = binding.breedSpinner
            val breedSpinnerAdapter =
                getPetBreedList(kindId)?.let { listBreedNameId ->
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        listBreedNameId.map { getString(it) }
                    )
                }
            breedSpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            breedSpinner.adapter = breedSpinnerAdapter

            breedSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.breed = position
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
                    viewModel.dateOfBirth = calendar.timeInMillis
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
                if (name.isEmpty() || dateOfBirth == 0.toLong() || weight == 0) {
                    Toast.makeText(
                        context,
                        getString(R.string.fill_up_all_fields), Toast.LENGTH_LONG
                    ).show()
                } else {
                    viewModel.addNewPetToDb()
                    findNavController().popBackStack()
                }
            }
        }
    }
}