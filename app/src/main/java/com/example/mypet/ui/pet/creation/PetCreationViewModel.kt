package com.example.mypet.ui.pet.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.domain.PetCreationRepository
import com.example.mypet.domain.pet.creation.PetCreationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetCreationViewModel @Inject constructor(
    private val petCreationRepository: PetCreationRepository
) : ViewModel() {

    var dateOfBirthTimeMillis: Long? = null
    var name: String = ""
    var breedOrdinal: Int = 0
    var kindOrdinal: Int = 0
    var weight: Int? = null
    var sex: Int = 0

    fun addNewPetToDb() {
        viewModelScope.launch(Dispatchers.IO) {
            petCreationRepository.addNewPetToDb(
                PetCreationModel(
                    id = DEFAULT_ID,
                    avatarUri = null,
                    name = name,
                    dateOfBirthTimeMillis = dateOfBirthTimeMillis,
                    weight = weight,
                    kindOrdinal = kindOrdinal,
                    breedOrdinal = breedOrdinal,
                    isActive = false,
                    sex = sex
                )
            )
        }
    }
}