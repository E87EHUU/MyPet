package com.example.mypet.ui.pet.creation

import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetCreationBinding
import com.example.mypet.domain.pet.kind.PetKind
import com.example.mypet.ui.getPetBreedList
import com.example.mypet.ui.snackMessage
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class PetCreationFragment : Fragment(R.layout.fragment_pet_creation) {

    private val binding by viewBinding(FragmentPetCreationBinding::bind)
    private val viewModel by viewModels<PetCreationViewModel>()

    private lateinit var breedSpinnerAdapter: ArrayAdapter<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onChoosePhotoButtonClickListener()
        initKindListSpinner()
        onKindItemSelectedListener()
        onBreedItemSelectedListener()
        onChooseDateOfBirthClickListener()
        onSaveNewPetButtonClickListener()
    }

    private fun onChoosePhotoButtonClickListener() {
        binding.appCompatButtonPetCreationChoosePhoto.setOnClickListener {
            requestPermission()
        }
    }

    private fun initKindListSpinner() {
        val adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                PetKind.values().map { getString(it.nameResId) }
            )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.appCompatTextViewPetCreationKindListSpinner.adapter = adapter
    }

    private fun onKindItemSelectedListener() {
        binding.appCompatTextViewPetCreationKindListSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    initBreedListSpinner(position)
                    viewModel.kindOrdinal = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Действие при отсутствии выбора
                }
            }
    }

    private fun initBreedListSpinner(kindId: Int) {
        if (getPetBreedList(kindId) == null) {
            binding.appCompatSpinnerPetCreationBreedListSpinner.visibility = View.GONE
            binding.appCompatTextViewPetCreationBreedListSpinnerTittle.visibility = View.GONE
        } else {
            binding.appCompatSpinnerPetCreationBreedListSpinner.visibility = View.VISIBLE
            binding.appCompatTextViewPetCreationBreedListSpinnerTittle.visibility = View.VISIBLE

            if (!::breedSpinnerAdapter.isInitialized) {
                breedSpinnerAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    mutableListOf()
                )
                breedSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.appCompatSpinnerPetCreationBreedListSpinner.adapter = breedSpinnerAdapter
            }

            getPetBreedList(kindId)?.let { listBreedNameId ->
                breedSpinnerAdapter.clear()
                breedSpinnerAdapter.addAll(listBreedNameId.map { getString(it) })
                breedSpinnerAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun onBreedItemSelectedListener() {
        binding.appCompatSpinnerPetCreationBreedListSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.breedOrdinal = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Действие при отсутствии выбора
                }
            }
    }

    private fun onChooseDateOfBirthClickListener() {
        binding.appCompatImageViewPetCreationCalendar.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    calendar.set(selectedYear, selectedMonth, selectedDay)
                    val selectedDate =
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
                    binding.appCompatTextViewPetCreationSelectedDate.text = selectedDate
                    viewModel.dateOfBirthTimeMillis = calendar.timeInMillis
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }

    private fun onSaveNewPetButtonClickListener() {
        binding.appCompatButtonPetCreationSave.setOnClickListener {
            viewModel.name = binding.textInputEditTextPetCreationName.text.toString()
            viewModel.weight =
                binding.textInputEditTextPetCreationWeight.text?.toString()?.toIntOrNull()
            with(viewModel) {
                if (name.isEmpty() || dateOfBirthTimeMillis == null || weight == null) {
                    view?.snackMessage(getString(R.string.fill_up_all_fields))
                } else {
                    viewModel.addNewPetToDb()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            Glide.with(this)
                .load(imageUri)
                .circleCrop()
                .placeholder(R.drawable.baseline_add_24)
                .into(binding.appCompatImageViewPetCreationAvatar)
            viewModel.avatarUri = imageUri.toString()
        }

    private fun pickImageFromGallery() {
        getContent.launch("image/*")
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted)
                pickImageFromGallery()
        }

    private fun requestPermission() {
        IMAGE_SELECTION_PERMISSION = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                IMAGE_SELECTION_PERMISSION
            ) -> {
                pickImageFromGallery()
            }

            else -> {
                requestPermissionLauncher.launch(IMAGE_SELECTION_PERMISSION)
            }
        }
    }

    companion object {
        var IMAGE_SELECTION_PERMISSION = "image_selection_permission"
    }
}