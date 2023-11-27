package com.example.mypet.ui.pet.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.PetCreationRepository
import com.example.mypet.domain.pet.PetModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetCreationViewModel @Inject constructor(
    private val petCreationRepository: PetCreationRepository
) : ViewModel() {

    var dateOfBirth: Long = 0
    var name: String = ""
    var breed: Int = 0
    var kind: Int = 0
    var weight: Int = 0

    fun addNewPetToDb() {
        viewModelScope.launch(Dispatchers.IO) {
            petCreationRepository.addNewPetToDb(
                PetModel(
                    1,
                    null,
                    name,
                    dateOfBirth.toInt(),
                    weight,
                    kind,
                    breed,
                    true
                )
            )
        }
    }
}