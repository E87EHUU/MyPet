package com.example.mypet.ui.pet.care

import com.example.mypet.domain.pet.care.PetCareModel

interface PetCareCallback {
    fun onPetCareClick(petCareModel: PetCareModel)
}