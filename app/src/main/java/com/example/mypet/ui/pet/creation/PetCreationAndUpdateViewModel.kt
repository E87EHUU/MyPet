package com.example.mypet.ui.pet.creation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.domain.PetCreationAndUpdateRepository
import com.example.mypet.domain.pet.creation.PetCreationAndUpdateModel
import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.utils.DEFAULT_INTEGER_VALUES
import com.example.mypet.utils.DEFAULT_STRING_VALUES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetCreationAndUpdateViewModel @Inject constructor(
    private val petCreationAndUpdateRepository: PetCreationAndUpdateRepository
) : ViewModel() {

    var avatarUri: String = DEFAULT_STRING_VALUES
    var dateOfBirth: String? = DEFAULT_STRING_VALUES
    var name: String = DEFAULT_STRING_VALUES
    var breedOrdinal: Int = DEFAULT_INTEGER_VALUES
    var kindOrdinal: Int = DEFAULT_INTEGER_VALUES
    var weight: Int? = DEFAULT_INTEGER_VALUES
    var sex: Int = DEFAULT_INTEGER_VALUES
    var petId: Int = DEFAULT_INTEGER_VALUES

    private val _localPetForUpdate = MutableLiveData<PetModel>()
    val localPetForUpdate: LiveData<PetModel>
        get() = _localPetForUpdate

    fun addOrUpdatePetInDb() {
        if (petId > DEFAULT_INTEGER_VALUES) {
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
            _localPetForUpdate.postValue(petCreationAndUpdateRepository.getPetFromDbForUpdateDetails(petId))
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
        kindOrdinal = kindOrdinal,
        breedOrdinal = breedOrdinal,
        isActive = false,
        sex = sex
    )
}