package com.example.mypet.ui.pet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.PetRepository
import com.example.mypet.domain.care.CareTypes
import com.example.mypet.domain.pet.PetModel
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.food.PetFoodAlarmModel
import com.example.mypet.domain.pet.kind.PetKind
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
    private val _pet = MutableStateFlow<List<PetModel>>(emptyList())
    val pet = _pet.asStateFlow()

    private val _food = MutableStateFlow<List<PetFoodAlarmModel>>(emptyList())
    val food = _food.asStateFlow()

    private val _care = MutableStateFlow<List<PetCareModel>>(emptyList())
    val care = _care.asStateFlow()

    init {
        updatePet()
    }

    private fun updatePet() = viewModelScope.launch(Dispatchers.IO) {
        petRepository.getPet()
            .collectLatest { _pet.value = it }
    }

    fun updatePetDetail(petModel: PetModel) {
        updateFood(petModel.id)
        updateCare(petModel)
    }

    private fun updateFood(petId: Int) = viewModelScope.launch(Dispatchers.IO) {
        petRepository.getFood(petId)
            .collectLatest { _food.value = it }
    }

    private fun updateCare(petModel: PetModel) = viewModelScope.launch(Dispatchers.IO) {
        petRepository.getCare(petModel.id)
            .collectLatest { _care.value = getCares(petModel) }
    }

    private fun getCares(petModel: PetModel) =
        when (petModel.kindOrdinal) {
            PetKind.CAT.ordinal -> {
                listOf(
                    PetCareModel(
                        0,
                        CareTypes.BATH.iconResId,
                        CareTypes.BATH.titleStringRes,
                        "",
                        -1
                    ),
                    PetCareModel(
                        0,
                        CareTypes.COMBING_THE_WOOL.iconResId,
                        CareTypes.COMBING_THE_WOOL.titleStringRes,
                        "",
                        -1
                    ),
                )
            }

            else -> emptyList()
        }

    fun deletePet(petId: Int) = viewModelScope.launch(Dispatchers.IO) {
        petRepository.deletePet(petId)
    }
}