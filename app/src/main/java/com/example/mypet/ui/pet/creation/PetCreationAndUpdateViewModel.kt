package com.example.mypet.ui.pet.creation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.domain.PetCreationAndUpdateRepository
import com.example.mypet.domain.pet.creation.PetCreationAndUpdateModel
import com.example.mypet.domain.pet.list.PetListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetCreationAndUpdateViewModel @Inject constructor(
    private val petCreationAndUpdateRepository: PetCreationAndUpdateRepository
) : ViewModel() {

    var petId: Int = DEFAULT_ID
    var name: String = DEFAULT_STRING_VALUE
    var avatarUri: String? = null
    var kindOrdinal: Int? = null
    var breedOrdinal: Int? = null
    var dateOfBirth: String? = null
    var weight: Int? = null
    var sex: Int? = null

    private val _localPetForUpdate = MutableLiveData<PetListModel>()
    val localPetForUpdate: LiveData<PetListModel>
        get() = _localPetForUpdate

    fun addOrUpdatePetInDb() {
        if (petId == DEFAULT_ID) addNewPetToDb()
        else updatePetDetailsInDb()
    }

    private fun addNewPetToDb() {
        viewModelScope.launch(Dispatchers.IO) {
            petCreationAndUpdateRepository.addNewPetToDb(
                viewModelVariablesToPetCreationModel(DEFAULT_ID)
            )
        }
    }

    fun getPetFromDbForUpdateDetails(petId: Int) {
        this.petId = petId

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

    companion object {
        const val DEFAULT_STRING_VALUE = ""
        const val DEFAULT_SEX_MALE_VALUE = 1
        const val DEFAULT_SEX_FEMALE_VALUE = 0
    }
}