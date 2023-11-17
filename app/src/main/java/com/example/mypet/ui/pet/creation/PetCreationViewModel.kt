package com.example.mypet.ui.pet.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.data.local.room.model.LocalPetKindModel
import com.example.mypet.domain.PetCreationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetCreationViewModel @Inject constructor(
    private val petCreationRepository: PetCreationRepository
) : ViewModel() {

    var dateOfBirth: String = ""
    var name: String = ""
    var breed: Int = 0
    var kind: Int = 0
    var weight: Int = 0

    private val _kindList = MutableStateFlow<List<LocalPetKindModel>>(emptyList())
    val kindList = _kindList.asStateFlow()

    private fun getKindList() = viewModelScope.launch(Dispatchers.IO) {
        petCreationRepository.getKindList()
            .collectLatest { _kindList.value = it }
    }

    private val _breedList = MutableStateFlow<List<String>>(emptyList())
    val breedList = _breedList.asStateFlow()

    fun getBreedList(kindId: Int) = viewModelScope.launch(Dispatchers.IO) {
        petCreationRepository.getBreedList(kindId)
            .collectLatest {
                _breedList.value = it.map { breed ->
                    breed.breedName
                }
            }
    }

    fun addNewPetToDb() {
        viewModelScope.launch(Dispatchers.IO) {
            petCreationRepository.addNewPetToDb(name, kind, breed, dateOfBirth, weight)
        }
    }

    init {
        getKindList()
    }
}