package com.example.mypet.ui.pet

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.PopupMenu
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
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.domain.pet.food.PetFoodModel
import com.example.mypet.ui.getActionBar
import com.example.mypet.ui.getPetIcon
import com.example.mypet.ui.getPetName
import com.example.mypet.ui.pet.care.PetCareAdapter
import com.example.mypet.ui.pet.care.PetCareAdapterCallback
import com.example.mypet.ui.pet.food.PetFoodAdapter
import com.example.mypet.ui.pet.food.PetFoodAdapterCallback
import com.example.mypet.ui.pet.list.OnAddPetClickListener
import com.example.mypet.ui.pet.list.OnPetClickListener
import com.example.mypet.ui.pet.list.PetListAdapter
import com.example.mypet.ui.snackMessage
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
                navToFoodDetail(petFoodModel)
            }
        }
    private val petFoodAdapter = PetFoodAdapter(petFoodAdapterCallback)

    private val petCareAdapterCallback =
        object : PetCareAdapterCallback {
            override fun onItemClick(petCareModel: PetCareModel) {
                navToCareDetail(petCareModel)
            }
        }
    private val petCareAdapter = PetCareAdapter(petCareAdapterCallback)

    override fun onResume() {
        super.onResume()
        getActionBar()?.hide()
    }

    override fun onStop() {
        super.onStop()
        getActionBar()?.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updatePetList()
        initView()
        startObservePetList()
        startObservePetFoodList()
        startObservePetCareList()
        initMenuPetAction()
        initListeners()
    }

    private fun initView() {
        getActionBar()?.hide()
        binding.recyclerViewPetList.adapter = petListAdapter
        binding.recyclerViewPetFoodList.adapter = petFoodAdapter
        binding.recyclerViewPetCareList.adapter = petCareAdapter
    }

    private fun startObservePetList() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.petList.collectLatest { petModels ->
                    if (petModels.isNotEmpty()) onNotEmptyPetModels(petModels)
                    else {
                        onEmptyPetModels()
                    }
                }
            }
        }
    }

    private fun onNotEmptyPetModels(petModels: List<PetModel>) {
        petListAdapter.setPetList(petModels)
        val activePetModel = petModels.find { it.isActive } ?: petModels.first()
        viewModel.activePetMyId = activePetModel.id
        onPetUpdate(activePetModel)
    }

    private fun onEmptyPetModels() {
        petListAdapter.setPetList(emptyList())
        viewModel.activePetMyId = null
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

    private fun initListeners() {
        binding.constraintLayoutPetFood.setOnClickListener { navToFood() }
        binding.constraintLayoutPetCare.setOnClickListener { navToCare() }
    }

    private fun onPetUpdate(petModel: PetModel) {
        viewModel.activePetMyId = petModel.id

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
            binding.textViewPetAgeText.text = petModel.age.toString()
            binding.materialCardViewPetAge.isVisible = true
        } ?: run {
            binding.materialCardViewPetAge.isVisible = false
        }

        petModel.weight?.let {
            binding.textViewPetWeightText.text = petModel.weight.toString()
            binding.materialCardViewPetWeight.isVisible = true
        } ?: run {
            binding.materialCardViewPetWeight.isVisible = false
        }
    }

    override fun onAddPetClick() {
        findNavController().navigate(R.id.petCreationFragment)
    }

    override fun onPetClick(pet: PetModel) {
        onPetUpdate(pet)
    }

    private fun navToFood() {
        viewModel.activePetMyId?.let {
            val directions = PetFragmentDirections.actionPetFragmentToFoodFragment(it)
            findNavController().navigate(directions)
        }
    }

    private fun navToFoodDetail(petFoodModel: PetFoodModel) {
        viewModel.activePetMyId?.let {
            val directions =
                PetFragmentDirections.actionPetFragmentToFoodDetailFragment(it, petFoodModel.id)
            findNavController().navigate(directions)
        }
    }

    private fun navToCare() {
        viewModel.activePetMyId?.let {
            val directions = PetFragmentDirections.actionPetFragmentToCareFragment(it)
            findNavController().navigate(directions)
        }
    }

    private fun navToCareDetail(petCareModel: PetCareModel) {
        val directions =
            PetFragmentDirections.actionPetFragmentToCareDetailFragment(petCareModel.id)
        findNavController().navigate(directions)
    }

    private fun initMenuPetAction() {
        binding.imageViewPopupMenuPetAction.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.menuInflater.inflate(R.menu.pet_action_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.pet_menu_item_edit_pet -> {
                        // Add Edit pet
                        true
                    }

                    R.id.pet_menu_item_delete_pet -> {
                        showDeletePetDialog()
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    private fun showDeletePetDialog() {
        val deletePetAlertDialog = layoutInflater.inflate(R.layout.alert_dialog_delete_pet, null)

        val alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setView(deletePetAlertDialog)
            .create()

        deletePetAlertDialog.findViewById<Button>(R.id.buttonAcceptDeletePetDialog)
            .setOnClickListener {
                val activePetId = viewModel.activePetMyId

                if (activePetId != null) {
                    viewModel.deletePet(activePetId)
                    alertDialog.dismiss()
                    view?.snackMessage(getString(R.string.alert_dialog_delete_pet_successful))
                } else {
                    view?.snackMessage(getString(R.string.alert_dialog_delete_pet_error))
                }
            }

        deletePetAlertDialog.findViewById<Button>(R.id.buttonCancelDeletePetDialog)
            .setOnClickListener {
                alertDialog.dismiss()
            }

        alertDialog.show()
    }
}