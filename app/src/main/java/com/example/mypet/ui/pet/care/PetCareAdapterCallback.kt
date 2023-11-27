package com.example.mypet.ui.pet.care

import com.example.mypet.domain.pet.care.PetCareModel

interface PetCareAdapterCallback {
    fun onItemClick(petCareModel: PetCareModel)
}