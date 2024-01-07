package com.example.mypet.ui.pet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.PetRepository
import com.example.mypet.domain.care.CareTypes
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.food.PetFoodModel
import com.example.mypet.domain.pet.kind.PetKind
import com.example.mypet.domain.pet.list.PetListMainModel
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
    private val _pet = MutableStateFlow<List<PetListMainModel>?>(null)
    val pet = _pet.asStateFlow()

    private val _food = MutableStateFlow<PetFoodModel?>(null)
    val food = _food.asStateFlow()

    private val _care = MutableStateFlow<List<PetCareModel>?>(null)
    val care = _care.asStateFlow()

    var activePetListId: Int? = null

    init {
        updatePet()
    }

    private fun updatePet() = viewModelScope.launch(Dispatchers.IO) {
        petRepository.getPetListMainModels()
            .collectLatest {
                if (it.isEmpty()) {
                    _food.value = null
                    _care.value = null
                }

                _pet.value = it
            }
    }

    fun updatePetDetail(activePetListMainModel: PetListMainModel?) {
        activePetListMainModel?.let {
            activePetListId = it.id
            updateFood(it.id)
            updateCare(it)
        } ?: run {
            _food.value = null
            _care.value = null
        }
    }

    private fun updateFood(petId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            petRepository.getPetFoodModel(petId)
                .collectLatest { _food.value = it }
        }

    private fun updateCare(petListMainModel: PetListMainModel) =
        viewModelScope.launch(Dispatchers.IO) {
            petRepository.getCareModels(petListMainModel.id)
                .collectLatest { _care.value = getCares(petListMainModel, it) }
        }

    private fun getCares(
        petListMainModel: PetListMainModel,
        petCareModel: List<PetCareModel>
    ): MutableList<PetCareModel> {
        val petCares = mutableListOf<PetCareModel>()

        petCares.add(petCareModel.getCare(CareTypes.BATH))
        petCares.add(petCareModel.getCare(CareTypes.WALK))
        petCares.add(petCareModel.getCare(CareTypes.MEDICINE))
        petCares.add(petCareModel.getCare(CareTypes.GROOMING))
        petCares.add(petCareModel.getCare(CareTypes.TRAINING))
        petCares.add(petCareModel.getCare(CareTypes.VACCINATION))
        petCares.add(petCareModel.getCare(CareTypes.VITAMIN))

        when (petListMainModel.kindOrdinal) {
            PetKind.CAT.ordinal -> {
                petCares.add(petCareModel.getCare(CareTypes.AGAINST_FLEAS_WORMS))
                petCares.add(petCareModel.getCare(CareTypes.AGAINST_FLEAS_AND_TICKS))
            }

            PetKind.DOG.ordinal -> {
                petCares.add(petCareModel.getCare(CareTypes.COMBING_THE_WOOL))
                petCares.add(petCareModel.getCare(CareTypes.AGAINST_FLEAS_WORMS))
                petCares.add(petCareModel.getCare(CareTypes.AGAINST_FLEAS_AND_TICKS))
            }
        }

        return petCares
    }

    private fun List<PetCareModel>.getCare(careTypes: CareTypes) =
        find { it.careType == careTypes } ?: PetCareModel(careType = careTypes)

    fun deletePet(petId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            petRepository.deletePet(petId)
        }
}