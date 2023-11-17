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
    private val petRepository: PetRepository,
) : ViewModel() {
    var activePetId: Int? = null

    private val _petList = MutableStateFlow<List<PetModel>>(emptyList())
    val petList = _petList.asStateFlow()

    fun updatePetList() = viewModelScope.launch(Dispatchers.IO) {
        if (_petList.value.isEmpty())
            petRepository.observePetList()
                .collectLatest { _petList.value = it }
    }
}