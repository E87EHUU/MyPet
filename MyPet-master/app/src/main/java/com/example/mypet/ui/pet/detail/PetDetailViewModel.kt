package com.example.mypet.ui.pet.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.example.mypet.domain.PetDetailRepository
import com.example.mypet.domain.pet.detail.PetModel
import javax.inject.Inject

@HiltViewModel
class PetDetailViewModel @Inject constructor(
    private val petDetailRepository: PetDetailRepository
) : ViewModel() {
    private val _pet = MutableStateFlow<PetModel?>(null)
    val pet = _pet.asStateFlow()

    fun updatePet() = viewModelScope.launch(Dispatchers.IO) {
        petDetailRepository.observePetDetail()
            .collectLatest { _pet.value = it }
    }
}