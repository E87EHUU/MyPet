package com.example.mypet.ui.pet.creation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.domain.PetCreationAndUpdateRepository
import com.example.mypet.domain.pet.creation.PetCreationAndUpdateModel
import com.example.mypet.domain.pet.list.PetListMainModel
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
    var dateOfBirth: Long? = null
    var weight: Int? = null
    var sexOrdinal: Int? = null

    private val _localPetForUpdate = MutableLiveData<PetListMainModel>()
    val localPetForUpdate: LiveData<PetListMainModel>
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
            petCreationAndUpdateRepository.getPetFromDbForUpdateDetails(petId)?.let {
                _localPetForUpdate.postValue(it)
                name = it.name
                avatarUri = it.avatarUri
                kindOrdinal = it.kindOrdinal
                breedOrdinal = it.breedOrdinal
                dateOfBirth = it.dateOfBirth
                weight = it.weight
                sexOrdinal = it.sex
            }
        }
    }

    private fun updatePetDetailsInDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val petCreationAndUpdateModel = viewModelVariablesToPetCreationModel(petId)
            println(petCreationAndUpdateModel)
            petCreationAndUpdateRepository.updatePetDetailsInDb(petCreationAndUpdateModel)
        }
    }

    private fun viewModelVariablesToPetCreationModel(id: Int) =
        PetCreationAndUpdateModel(
            id = id,
            avatarUri = avatarUri,
            name = name,
            dateOfBirth = dateOfBirth,
            weight = weight,
            kindOrdinal = kindOrdinal!!,
            breedOrdinal = breedOrdinal,
            isActive = false,
            sex = sexOrdinal
        )

    companion object {
        const val DEFAULT_STRING_VALUE = ""
    }
}