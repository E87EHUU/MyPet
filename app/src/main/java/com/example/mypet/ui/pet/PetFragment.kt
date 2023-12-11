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
import com.example.mypet.domain.alarm.AlarmMinModel
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.list.PetListModel
import com.example.mypet.ui.getActionBar
import com.example.mypet.ui.pet.care.main.PetCareMainCallback
import com.example.mypet.ui.pet.food.alarm.PetFoodAlarmCallback
import com.example.mypet.ui.pet.main.PetMainCallback
import com.example.mypet.utils.DEFAULT_INTEGER_VALUE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PetFragment : Fragment(R.layout.fragment_pet),
    PetMainCallback, PetFoodAlarmCallback, PetCareMainCallback {
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

        lifecycleScope.launch(Dispatchers.IO) {
            // FIXME Ничего лучше не придумал. Необходимо чтобы не дергался интерфейс при переходе между фрагментами.
            initObservePet()
            initObserveFood()
            initObserveCare()
        }
    }

    private fun initView() {
        getActionBar()?.hide()

        binding.root.itemAnimator = null
        binding.root.adapter = adapter
    }

    private fun initObservePet() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.pet.collectLatest { petListModels ->
                    petListModels?.let {
                        adapter.petListModel = it
                        adapter.notifyItemChanged(PetAdapter.PET_POSITION)
                    }
                }
            }
        }
    }

    private fun initObserveFood() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.food.collectLatest {
                    it?.let {
                        adapter.petFoodModel = it
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
                    it?.let {
                        adapter.care = it
                        adapter.notifyItemChanged(PetAdapter.CARE_POSITION)
                    }
                }
            }
        }
    }

    private fun navToCare(petCareModel: PetCareModel) {
        viewModel.activePetListModel?.let {
            val directions = PetFragmentDirections.actionPetFragmentToNavigationPetCare(
                petId = it.id,
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
            PetFragmentDirections.actionPetToPetCreationAndUpdateFragment(DEFAULT_INTEGER_VALUE)
        findNavController().navigate(directions)
    }

    override fun onClickPet(petListModel: PetListModel?) {
        viewModel.updatePetDetail(petListModel)
        adapter.activePetListModel = viewModel.activePetListModel
    }

    override fun onClickPetDelete(petListModel: PetListModel) {
        viewModel.deletePet(petListModel.id)
    }

    override fun onClickPetEdit(petListModel: PetListModel) {
        val directions =
            PetFragmentDirections.actionPetToPetCreationAndUpdateFragment(petListModel.id)
        findNavController().navigate(directions)
    }
}