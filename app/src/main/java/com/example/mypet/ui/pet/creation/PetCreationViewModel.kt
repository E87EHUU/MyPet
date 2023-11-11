package com.example.mypet.ui.pet.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _kindList = MutableStateFlow<List<String>>(emptyList())
    val kindList = _kindList.asStateFlow()

    private fun getKindList() = viewModelScope.launch(Dispatchers.IO) {
        petCreationRepository.getKindList()
            .collectLatest { _kindList.value = it }
    }

    init {
        getKindList()
    }
}