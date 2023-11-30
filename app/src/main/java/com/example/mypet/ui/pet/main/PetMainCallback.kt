package com.example.mypet.ui.pet.main

import com.example.mypet.domain.pet.PetModel

interface PetMainCallback {
    fun onPetAddClick()
    fun onPetClick(petListModel: PetModel)
}