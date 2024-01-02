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
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.domain.alarm.AlarmMinModel
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.list.PetListMainModel
import com.example.mypet.ui.getActionBar
import com.example.mypet.ui.pet.care.main.PetCareMainCallback
import com.example.mypet.ui.pet.food.alarm.PetFoodAlarmCallback
import com.example.mypet.ui.pet.main.PetMainCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetFragment : Fragment(R.layout.fragment_pet),
    PetMainCallback, PetFoodAlarmCallback, PetCareMainCallback {
    private val binding by viewBinding(FragmentPetBinding::bind)
    private val viewModel by viewModels<PetViewModel>()

    private val adapter = PetAdapter(this, this, this)

    override fun onStart() {
        super.onStart()
        getActionBar()?.hide()
    }

    override fun onResume() {
        super.onResume()
        postponeEnterTransition()
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
        startPostponedEnterTransition()
    }

    private fun initView() {
        getActionBar()?.hide()

        binding.root.itemAnimator = null
        binding.root.adapter = adapter
    }

    private fun initObservePet() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.pet.collectLatest { petListMainModels ->
                    adapter.petListMainModel = petListMainModels
                    adapter.notifyItemChanged(PetAdapter.PET_POSITION)
                }
            }
        }
    }

    private fun initObserveFood() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.food.collectLatest {
                    adapter.petFoodModel = it

                    binding.root.post {
                        adapter.notifyItemChanged(PetAdapter.FOOD_POSITION)
                    }
                }
            }
        }
    }

    private fun initObserveCare() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.care.collectLatest {
                    adapter.petCareModels = it

                    binding.root.post {
                        adapter.notifyItemChanged(PetAdapter.CARE_POSITION)
                    }
                }
            }
        }
    }

    private fun navToCare(petCareModel: PetCareModel) {
        viewModel.activePetListId?.let {
            val directions = PetFragmentDirections.actionPetFragmentToNavigationPetCare(
                petId = it,
                careId = petCareModel.id,
                careTypeOrdinal = petCareModel.careType.ordinal
            )
            findNavController().navigate(directions)
        }
    }

    override fun onClickPetFood(petCareModel: PetCareModel) {
        navToCare(petCareModel)
    }

    override fun onClickAlarm(alarmMinModel: AlarmMinModel) {

    }

    override fun onPetCareClick(petCareModel: PetCareModel) {
        navToCare(petCareModel)
    }

    override fun onClickPetAdd() {
        val directions =
            PetFragmentDirections.actionPetToPetCreationAndUpdateFragment(DEFAULT_ID)
        findNavController().navigate(directions)
    }

    override fun onClickPet(petListMainModel: PetListMainModel?) {
        viewModel.updatePetDetail(petListMainModel)
        adapter.activePetListId = viewModel.activePetListId
    }

    override fun onClickPetDelete(petListMainModel: PetListMainModel) {
        viewModel.deletePet(petListMainModel.id)
        adapter.activePetListId = null
    }

    override fun onClickPetEdit(petListMainModel: PetListMainModel) {
        val directions =
            PetFragmentDirections.actionPetToPetCreationAndUpdateFragment(petListMainModel.id)
        findNavController().navigate(directions)
    }
}