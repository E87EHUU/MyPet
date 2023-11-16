package com.example.mypet.ui.pet

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetGraphViewModel @Inject constructor() : ViewModel() {
    var activePetId: Int? = null
}