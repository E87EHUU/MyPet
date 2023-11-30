package com.example.mypet.ui.pet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentPetBinding
import com.example.mypet.domain.pet.PetModel
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.food.PetFoodAlarmModel
import com.example.mypet.ui.getActionBar
import com.example.mypet.ui.pet.care.PetCareCallback
import com.example.mypet.ui.pet.food.PetFoodCallback
import com.example.mypet.ui.pet.main.PetMainCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetFragment : Fragment(R.layout.fragment_pet),
    PetMainCallback, PetFoodCallback, PetCareCallback {
    private val binding by viewBinding(FragmentPetBinding::bind)
    private val viewModel by viewModels<PetViewModel>()

    private val adapter = PetAdapter(this, this, this)


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

        initView()
        initObservePet()
        initObserveFood()
        initObserveCare()
        //initMenuPetAction()
    }

    private fun initView() {
        getActionBar()?.hide()

        binding.root.adapter = adapter
    }

    private fun initObservePet() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.pet.collectLatest {
                    adapter.petListModel = it
                    adapter.notifyItemChanged(PetAdapter.PET_POSITION)
                }
            }
        }
    }

    private fun initObserveFood() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.food.collectLatest {
                    adapter.food = it
                    adapter.notifyItemChanged(PetAdapter.FOOD_POSITION)
                }
            }
        }
    }

    private fun initObserveCare() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.care.collectLatest {
                    adapter.care = it
                    adapter.notifyItemChanged(PetAdapter.CARE_POSITION)
                }
            }
        }
    }

    private fun navToFood() {
        /*        viewModel.activePetId?.let {
                    val directions = PetFragmentDirections
                        .actionPetFragmentToCareFragment(it, CareTypes.FOOD.ordinal)
                    findNavController().navigate(directions)
                }*/
    }

    private fun navToFoodDetail(petFoodModel: PetFoodAlarmModel) {
        /*        viewModel.activePetId?.let {
                    val directions =
                        PetFragmentDirections.actionPetFragmentToFoodDetailFragment(it, petFoodModel.f)
                    findNavController().navigate(directions)
                }*/
    }

    private fun navToCare() {
        /*        viewModel.activePetId?.let {
                    val directions = PetFragmentDirections.actionPetFragmentToCareFragment(it)
                    findNavController().navigate(directions)
                }*/
    }

    private fun navToCareDetail(petCareModel: PetCareModel) {
        /*        val directions =
                    PetFragmentDirections.actionPetFragmentToCareDetailFragment(petCareModel.id)
                findNavController().navigate(directions)*/
    }

    /*    private fun initMenuPetAction() {
            binding.cardViewPopupMenuPetAction.setOnClickListener {
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
        }*/

    private fun showDeletePetDialog() {
        /*        val deletePetAlertDialog = layoutInflater.inflate(R.layout.alert_dialog_delete_pet, null)

                val alertDialog = MaterialAlertDialogBuilder(requireContext())
                    .setView(deletePetAlertDialog)
                    .create()

                deletePetAlertDialog.findViewById<Button>(R.id.buttonAcceptDeletePetDialog)
                    .setOnClickListener {
                        val activePetId = viewModel.activePetId

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

                alertDialog.show()*/
    }

    override fun onPetFoodAlarmClick(petFoodAlarmModel: PetFoodAlarmModel) {
        navToFoodDetail(petFoodAlarmModel)
    }

    override fun onPetCareClick(petCareModel: PetCareModel) {
        navToCareDetail(petCareModel)
    }

    override fun onPetAddClick() {
        findNavController().navigate(R.id.petCreationFragment)
    }

    override fun onPetClick(petModel: PetModel) {
        viewModel.updatePetDetail(petModel)
    }
}