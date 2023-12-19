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
import com.example.mypet.domain.pet.list.PetListModel
import com.example.mypet.ui.getPetBreedList
import com.example.mypet.ui.getPetIcon
import com.example.mypet.ui.getToolbar
import com.example.mypet.ui.pet.creation.PetCreationAndUpdateViewModel.Companion.DEFAULT_SEX_FEMALE_VALUE
import com.example.mypet.ui.pet.creation.PetCreationAndUpdateViewModel.Companion.DEFAULT_SEX_MALE_VALUE
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
        binding.chipPetCreationMale.setOnClickListener {
            viewModel.sex = DEFAULT_SEX_MALE_VALUE
            binding.chipPetCreationMale.isChecked = true
            binding.chipPetCreationFemale.isChecked = false
        }

        binding.chipPetCreationFemale.setOnClickListener {
            viewModel.sex = DEFAULT_SEX_FEMALE_VALUE
            binding.chipPetCreationMale.isChecked = false
            binding.chipPetCreationFemale.isChecked = true
        }
    }

    private fun initPetDetailsForUpdate(localPetModel: PetListModel) {
        fillViewModelFields(localPetModel)

        with(binding) {

            updateUIAvatar()

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

        if (localPetModel.sex == PetSex.FEMALE.ordinal) {
            binding.chipPetCreationFemale.isChecked = true
        } else if (localPetModel.sex == PetSex.MALE.ordinal) {
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

    private fun fillViewModelFields(localPetModel: PetListModel) {
        viewModel.dateOfBirth = localPetModel.age
        viewModel.avatarUri = localPetModel.avatarUri.toString()
        viewModel.kindOrdinal = localPetModel.kindOrdinal
        viewModel.breedOrdinal = localPetModel.breedOrdinal
        viewModel.sex = localPetModel.sex
    }

    private fun onChoosePhotoButtonClickListener() {
        binding.imageViewPetCreationAvatar.setOnClickListener {
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
                    viewModel.dateOfBirth = calendar.timeInMillis.toString()
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