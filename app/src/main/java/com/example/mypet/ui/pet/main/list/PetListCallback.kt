package com.example.mypet.ui.pet.main.list

import com.example.mypet.domain.pet.PetListModel

interface PetListCallback {
    fun onClickPetAdd()
    fun onClickPet(petListModel: PetListModel)
}