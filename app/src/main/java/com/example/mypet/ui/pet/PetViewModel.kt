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
class PetViewModel @Inject constructor(
    private val petDetailRepository: PetRepository,
) : ViewModel() {
    private val _petList = MutableStateFlow<List<PetModel>>(emptyList())
    val petList = _petList.asStateFlow()

    var activePetId: Int? = null

    private fun updatePetList() = viewModelScope.launch(Dispatchers.IO) {
        petDetailRepository.observePetList()
            .collectLatest { _petList.value = it }
    }

    init {
        updatePetList()
    }
}