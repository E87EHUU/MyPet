package com.example.mypet.ui.pet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.PetRepository
import com.example.mypet.domain.care.CareTypes
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.food.PetFoodModel
import com.example.mypet.domain.pet.kind.PetKind
import com.example.mypet.domain.pet.list.PetListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val petRepository: PetRepository,
) : ViewModel() {
    private val _pet = MutableStateFlow<List<PetListModel>?>(null)
    val pet = _pet.asStateFlow()

    private val _food = MutableStateFlow<PetFoodModel?>(null)
    val food = _food.asStateFlow()

    private val _care = MutableStateFlow<List<PetCareModel>?>(null)
    val care = _care.asStateFlow()

    var activePetListModel: PetListModel? = null

    init {
        updatePet()
    }

    private fun updatePet() = viewModelScope.launch(Dispatchers.IO) {
        petRepository.getPetListModels()
            .collectLatest { _pet.value = it }
    }

    fun updatePetDetail(petListModel: PetListModel?) {
        petListModel?.let {
            activePetListModel = it
            updateFood(it.id)
            updateCare(it)
        } ?: run {
            _food.value = null
            _care.value = null
        }
    }

    private fun updateFood(petId: Int) = viewModelScope.launch(Dispatchers.IO) {
        petRepository.getPetFoodModel(petId)
            .collectLatest { _food.value = it }
    }

    private fun updateCare(petListModel: PetListModel) = viewModelScope.launch(Dispatchers.IO) {
        petRepository.getCareModels(petListModel.id)
            .collectLatest { _care.value = getCares(petListModel, it) }
    }

    private fun getCares(petListModel: PetListModel, petCareModel: List<PetCareModel>) =
        when (petListModel.kindOrdinal) {
            PetKind.CAT.ordinal,
            PetKind.DOG.ordinal -> {
                listOf(
                    petCareModel.find { it.careType == CareTypes.BATH }
                        ?: PetCareModel(careType = CareTypes.BATH),

                    petCareModel.find { it.careType == CareTypes.COMBING_THE_WOOL }
                        ?: PetCareModel(careType = CareTypes.COMBING_THE_WOOL),

                    petCareModel.find { it.careType == CareTypes.AGAINST_FLEAS_WORMS }
                        ?: PetCareModel(careType = CareTypes.AGAINST_FLEAS_WORMS),

                    petCareModel.find { it.careType == CareTypes.AGAINST_FLEAS_AND_TICKS }
                        ?: PetCareModel(careType = CareTypes.AGAINST_FLEAS_AND_TICKS),

                    petCareModel.find { it.careType == CareTypes.WALK }
                        ?: PetCareModel(careType = CareTypes.WALK),
                )
            }

            else -> emptyList()
        }

    fun deletePet(petId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            petRepository.deletePet(petId)
        }
}