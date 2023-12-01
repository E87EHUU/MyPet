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
import com.example.mypet.domain.pet.PetListModel
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
                    binding.root.post {
                        adapter.notifyItemChanged(PetAdapter.FOOD_POSITION)
                    }
                }
            }
        }
    }

    private fun initObserveCare() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.care.collectLatest {
                    adapter.care = it
                    binding.root.post {
                        adapter.notifyItemChanged(PetAdapter.CARE_POSITION)
                    }
                }
            }
        }
    }

    private fun navToCare(petCareModel: PetCareModel) {
        val directions = PetFragmentDirections.actionPetFragmentToCareFragment(
            petCareModel.id,
            petCareModel.careType.ordinal
        )
        findNavController().navigate(directions)
    }

    override fun onPetFoodClick(petCareModel: PetCareModel) {
        navToCare(petCareModel)
    }

    override fun onPetFoodAlarmClick(petFoodAlarmModel: PetFoodAlarmModel) {

    }

    override fun onPetCareClick(petCareModel: PetCareModel) {
        navToCare(petCareModel)
    }

    override fun onClickPetAdd() {
        findNavController().navigate(R.id.petCreationFragment)
    }

    override fun onClickPet(petListModel: PetListModel?) {
        viewModel.updatePetDetail(petListModel)
    }

    override fun onClickPetDelete(petListModel: PetListModel) {
        viewModel.deletePet(petListModel.id)
    }

    override fun onClickPetEdit(petListModel: PetListModel) {
        TODO("Not yet implemented")
    }
}