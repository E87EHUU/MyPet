package com.example.mypet.ui.pet.care.main

import com.example.mypet.domain.pet.care.PetCareModel

interface PetCareMainCallback {
    fun onClickPetCare(petCareModel: PetCareModel)
    fun onClickPetCareAdd()
}