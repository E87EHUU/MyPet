package com.example.mypet.ui.pet.creation

import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetCreationBinding
import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.domain.pet.kind.PetKind
import com.example.mypet.ui.getPetBreedList
import com.example.mypet.ui.snackMessage
import com.example.mypet.utils.DEFAULT_INTEGER_VALUE
import com.example.mypet.utils.DEFAULT_SEX_FEMALE_VALUE
import com.example.mypet.utils.DEFAULT_SEX_MALE_VALUE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class PetCreationAndUpdateFragment : Fragment(R.layout.fragment_pet_creation) {

    private val binding by viewBinding(FragmentPetCreationBinding::bind)
    private val viewModel by viewModels<PetCreationAndUpdateViewModel>()

    private lateinit var kindListAdapter: ArrayAdapter<String>
    private lateinit var breedListAdapter: ArrayAdapter<String>

    private val args: PetCreationAndUpdateFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onChoosePhotoButtonClickListener()
        onWhichSexChipSelectedListener()
        initKindListView()
        onChooseDateOfBirthClickListener()
        onSaveNewPetButtonClickListener()

        collectPetDetailsIfNeedsToUpdate()
    }

    private fun collectPetDetailsIfNeedsToUpdate() {
        if (args.petMyId > DEFAULT_INTEGER_VALUE) {
            viewModel.petId = args.petMyId
            viewModel.getPetFromDbForUpdateDetails(args.petMyId)
            startObservePetDetailsForUpdate()
        }
    }

    private fun startObservePetDetailsForUpdate() {
        lifecycleScope.launch {
            viewModel.localPetForUpdate.observe(viewLifecycleOwner) {
                initBreedListView(it.kindOrdinal)
                initPetDetailsForUpdate(it)
            }
        }
    }

    private fun onWhichSexChipSelectedListener() {
        binding.petCreationChipMale.setOnClickListener {
            viewModel.sex = DEFAULT_SEX_MALE_VALUE
        }
        binding.petCreationChipFemale.setOnClickListener {
            viewModel.sex = DEFAULT_SEX_FEMALE_VALUE
        }
    }

    private fun initPetDetailsForUpdate(localPetModel: PetModel) {
        fillViewModelFields(localPetModel)
        with(binding) {
            Glide.with(this@PetCreationAndUpdateFragment)
                .load(localPetModel.avatarUri)
                .circleCrop()
                .placeholder(R.drawable.baseline_add_photo_alternate_24)
                .into(appCompatImageViewPetCreationAvatar)
            textInputEditTextPetCreationName.setText(localPetModel.name)
            autoCompleteTextViewPetCreationKindList.setText(
                kindListAdapter.getItem(
                    localPetModel.kindOrdinal
                ), false
            )
            localPetModel.breedOrdinal?.let {
                autoCompleteTextViewPetCreationBreedList.setText(
                    breedListAdapter.getItem(
                        localPetModel.breedOrdinal
                    ), false
                )
            }
            textInputEditTextPetCreationWeight.setText(localPetModel.weight)
            if (localPetModel.age != null) {
                textInputEditTextPetCreationDateOfBirth.setText(
                    SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    ).format(localPetModel.age.toLong())
                )
            }
        }
        if (localPetModel.sex == DEFAULT_SEX_FEMALE_VALUE) {
            binding.petCreationChipFemale.isChecked = true
        } else if (localPetModel.sex == DEFAULT_SEX_MALE_VALUE) {
            binding.petCreationChipMale.isChecked = true
        }
    }

    private fun fillViewModelFields(localPetModel: PetModel) {
        viewModel.dateOfBirth = localPetModel.age
        viewModel.avatarUri = localPetModel.avatarUri.toString()
        viewModel.kindOrdinal = localPetModel.kindOrdinal
        viewModel.breedOrdinal = localPetModel.breedOrdinal
        viewModel.sex = localPetModel.sex
    }

    private fun onChoosePhotoButtonClickListener() {
        binding.appCompatImageViewPetCreationAvatar.setOnClickListener {
            requestPermission()
        }
    }

    private fun initKindListView() {
        kindListAdapter = ArrayAdapter(requireContext(),
            R.layout.fragment_pet_creation_custom_dropdown_item,
            PetKind.values().map { getString(it.nameResId) })
        binding.autoCompleteTextViewPetCreationKindList.setAdapter(kindListAdapter)
        onKindItemSelectedListener()
    }

    private fun onKindItemSelectedListener() {
        binding.autoCompleteTextViewPetCreationKindList.setOnItemClickListener { _, _, position, _ ->
            viewModel.kindOrdinal = position
            initBreedListView(position)
        }
    }

    private fun initBreedListView(kindId: Int) {
        if (!::breedListAdapter.isInitialized) {
            breedListAdapter = ArrayAdapter(
                requireContext(),
                R.layout.fragment_pet_creation_custom_dropdown_item,
                mutableListOf()
            )
            binding.autoCompleteTextViewPetCreationBreedList.setAdapter(breedListAdapter)
        }
        if (getPetBreedList(kindId) == null) {
            binding.textInputLayoutPetCreationChoicePetBreed.visibility = View.GONE
        } else {
            binding.textInputLayoutPetCreationChoicePetBreed.visibility = View.VISIBLE
            getPetBreedList(kindId)?.let { listBreedNameId ->
                breedListAdapter.clear()
                breedListAdapter.addAll(listBreedNameId.map { getString(it) })
                breedListAdapter.notifyDataSetChanged()
            }
            onBreedItemSelectedListener()
        }
    }

    private fun onBreedItemSelectedListener() {
        binding.autoCompleteTextViewPetCreationBreedList.setOnItemClickListener { _, _, position, _ ->
            viewModel.breedOrdinal = position
        }
    }

    private fun onChooseDateOfBirthClickListener() {
        binding.textInputEditTextPetCreationDateOfBirth.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                    calendar.set(selectedYear, selectedMonth, selectedDay)
                    val selectedDate =
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
                    binding.textInputEditTextPetCreationDateOfBirth.setText(selectedDate)
                    viewModel.dateOfBirth = calendar.timeInMillis.toString()
                }, year, month, day
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
                if (name.isEmpty() || kindOrdinal == null || sex == null) {
                    view?.snackMessage(getString(R.string.fill_up_all_fields))
                } else {
                    viewModel.addOrUpdatePetInDb()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            Glide.with(this).load(imageUri).circleCrop()
                .into(binding.appCompatImageViewPetCreationAvatar)
            viewModel.avatarUri = imageUri.toString()
        }

    private fun pickImageFromGallery() {
        getContent.launch("image/*")
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) pickImageFromGallery()
        }

    private fun requestPermission() {
        IMAGE_SELECTION_PERMISSION = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(), IMAGE_SELECTION_PERMISSION
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