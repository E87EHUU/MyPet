package com.example.mypet.ui.pet.creation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.domain.PetCreationAndUpdateRepository
import com.example.mypet.domain.pet.creation.PetCreationAndUpdateModel
import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.utils.DEFAULT_INTEGER_VALUE
import com.example.mypet.utils.DEFAULT_NULL_VALUE
import com.example.mypet.utils.DEFAULT_STRING_VALUE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetCreationAndUpdateViewModel @Inject constructor(
    private val petCreationAndUpdateRepository: PetCreationAndUpdateRepository
) : ViewModel() {

    var avatarUri: String = DEFAULT_STRING_VALUE
    var dateOfBirth: String? = DEFAULT_NULL_VALUE
    var name: String = DEFAULT_STRING_VALUE
    var kindOrdinal: Int? = DEFAULT_NULL_VALUE
    var breedOrdinal: Int? = DEFAULT_NULL_VALUE
    var weight: Int? = DEFAULT_NULL_VALUE
    var sex: Int? = DEFAULT_NULL_VALUE
    var petId: Int = DEFAULT_INTEGER_VALUE

    private val _localPetForUpdate = MutableLiveData<PetModel>()
    val localPetForUpdate: LiveData<PetModel>
        get() = _localPetForUpdate

    fun addOrUpdatePetInDb() {
        if (petId > DEFAULT_INTEGER_VALUE) {
            updatePetDetailsInDb()
        } else {
            addNewPetToDb()
        }
    }

    private fun addNewPetToDb() {
        viewModelScope.launch(Dispatchers.IO) {
            petCreationAndUpdateRepository.addNewPetToDb(
                viewModelVariablesToPetCreationModel(DEFAULT_ID)
            )
        }
    }

    fun getPetFromDbForUpdateDetails(petId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _localPetForUpdate.postValue(
                petCreationAndUpdateRepository.getPetFromDbForUpdateDetails(petId)
            )
        }
    }

    private fun updatePetDetailsInDb() {
        viewModelScope.launch(Dispatchers.IO) {
            petCreationAndUpdateRepository.updatePetDetailsInDb(
                viewModelVariablesToPetCreationModel(petId)
            )
        }
    }

    private fun viewModelVariablesToPetCreationModel(id: Int) = PetCreationAndUpdateModel(
        id = id,
        avatarUri = avatarUri,
        name = name,
        dateOfBirth = dateOfBirth,
        weight = weight,
        kindOrdinal = kindOrdinal!!,
        breedOrdinal = breedOrdinal,
        isActive = false,
        sex = sex
    )
}