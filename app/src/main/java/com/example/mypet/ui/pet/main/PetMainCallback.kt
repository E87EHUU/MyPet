package com.example.mypet.ui.pet.main

import com.example.mypet.domain.pet.PetListModel

interface PetMainCallback {
    fun onClickPetAdd()
    fun onClickPet(petListModel: PetListModel)
    fun onClickPetDelete(petListModel: PetListModel)
    fun onClickPetEdit(petListModel: PetListModel)
}