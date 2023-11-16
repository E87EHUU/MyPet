package com.example.mypet.ui.pet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.PetRepository
import com.example.mypet.domain.pet.detail.PetModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetGraphViewModel @Inject constructor(
    private val petDetailRepository: PetRepository,
) : ViewModel() {
    var activePetId: Int? = null

    private val _petList = MutableStateFlow<List<PetModel>>(emptyList())
    val petList = _petList.asStateFlow()

    fun updatePetList(myPetId: Int?) = viewModelScope.launch(Dispatchers.IO) {
        if (activePetId == null || activePetId != myPetId)
            petDetailRepository.observePetList(activePetId)
                .collectLatest { _petList.value = it }
    }
}