package com.example.mypet.ui.pet.creation

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetCreationBinding
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.domain.pet.PetSex
import com.example.mypet.domain.pet.kind.PetKind
import com.example.mypet.domain.pet.list.PetListMainModel
import com.example.mypet.ui.getPetBreedList
import com.example.mypet.ui.getPetIcon
import com.example.mypet.ui.getToolbar
import com.example.mypet.ui.snackMessage
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

    override fun onStop() {
        super.onStop()
        getToolbar()
    }

    override fun onStart() {
        super.onStart()
        initToolbar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initKindListView()

        onChoosePhotoButtonClickListener()
        onWhichSexChipSelectedListener()
        onChooseDateOfBirthClickListener()

        collectPetDetailsIfNeedsToUpdate()
    }

    private fun initToolbar() {
        getToolbar()?.let { toolbar ->
            toolbar.inflateMenu(R.menu.toolbar_save)
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.toolbarSave -> {
                        onSaveNewPetButtonClickListener()
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun collectPetDetailsIfNeedsToUpdate() {
        if (args.petId != DEFAULT_ID) {
            viewModel.getPetFromDbForUpdateDetails(args.petId)
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
        with(binding) {
            binding.chipPetCreationMale.setOnClickListener {
                if (viewModel.sexOrdinal == PetSex.MALE.ordinal) {
                    viewModel.sexOrdinal = null
                    chipPetCreationMale.isChecked = false
                } else {
                    viewModel.sexOrdinal = PetSex.MALE.ordinal
                    chipPetCreationMale.isChecked = true
                    chipPetCreationFemale.isChecked = false
                }
            }

            binding.chipPetCreationFemale.setOnClickListener {
                if (viewModel.sexOrdinal == PetSex.FEMALE.ordinal) {
                    viewModel.sexOrdinal = null
                    chipPetCreationFemale.isChecked = false
                } else {
                    viewModel.sexOrdinal = PetSex.FEMALE.ordinal
                    chipPetCreationMale.isChecked = false
                    chipPetCreationFemale.isChecked = true
                }
            }
        }
    }

    private fun initPetDetailsForUpdate(petListMainModel: PetListMainModel) {
        with(binding) {

            updateUIAvatar()

            textInputEditTextPetCreationName.setText(petListMainModel.name)
            autoCompleteTextViewPetCreationKindList.setText(
                kindListAdapter.getItem(
                    petListMainModel.kindOrdinal
                ), false
            )

            petListMainModel.breedOrdinal?.let {
                autoCompleteTextViewPetCreationBreedList.setText(
                    breedListAdapter.getItem(
                        petListMainModel.breedOrdinal
                    ), false
                )
            }

            petListMainModel.weight?.let {
                textInputEditTextPetCreationWeight.setText(petListMainModel.weight.toString())
            }

            if (petListMainModel.dateOfBirth != null) {
                textInputEditTextPetCreationDateOfBirth.setText(
                    SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                    ).format(petListMainModel.dateOfBirth.toLong())
                )
            }
        }

        if (petListMainModel.sex == PetSex.FEMALE.ordinal) {
            binding.chipPetCreationFemale.isChecked = true
        } else if (petListMainModel.sex == PetSex.MALE.ordinal) {
            binding.chipPetCreationMale.isChecked = true
        }
    }

    private fun updateUIAvatar() {
        with(viewModel) {
            val icon = kindOrdinal?.let { getPetIcon(it, breedOrdinal) }
                ?: R.drawable.baseline_add_photo_alternate_24

            Glide.with(this@PetCreationAndUpdateFragment)
                .load(avatarUri)
                .circleCrop()
                .placeholder(icon)
                .into(binding.imageViewPetCreationAvatar)
        }
    }

    private fun onChoosePhotoButtonClickListener() {
        binding.imageViewPetCreationAvatar.setOnClickListener {
            requestPermission()
        }
    }

    private fun initKindListView() {
        kindListAdapter = ArrayAdapter(requireContext(),
            R.layout.fragment_pet_creation_custom_dropdown_item,
            PetKind.entries.map { getString(it.nameResId) })
        binding.autoCompleteTextViewPetCreationKindList.setAdapter(kindListAdapter)
        onKindItemSelectedListener()
    }

    private fun onKindItemSelectedListener() {
        binding.autoCompleteTextViewPetCreationKindList.setOnItemClickListener { _, _, position, _ ->
            viewModel.kindOrdinal = position
            viewModel.breedOrdinal = null

            updateUIAvatar()

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
            onBreedItemSelectedListener()
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
            binding.autoCompleteTextViewPetCreationBreedList.text.clear()
        }
    }

    private fun onBreedItemSelectedListener() {
        binding.autoCompleteTextViewPetCreationBreedList.setOnItemClickListener { _, _, position, _ ->
            viewModel.breedOrdinal = position
            updateUIAvatar()
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
                    viewModel.dateOfBirth = calendar.timeInMillis
                }, year, month, day
            )
            datePickerDialog.show()
        }
    }

    private fun onSaveNewPetButtonClickListener() {
        viewModel.name = binding.textInputEditTextPetCreationName.text.toString()
        try {
            viewModel.weight = binding.textInputEditTextPetCreationWeight.text.toString().toInt()
        } catch (_: Exception) {

        }

        with(viewModel) {
            if (name.isEmpty() || kindOrdinal == null) {
                view?.snackMessage(getString(R.string.pet_creation_fill_all_fields))
            } else {
                viewModel.addOrUpdatePetInDb()
                findNavController().popBackStack()
            }
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            activityResult.data?.data?.let {
                viewModel.avatarUri = it.toString()
                updateUIAvatar()
            }
        }

    private fun requestPermission() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        getContent.launch(intent)
    }
}