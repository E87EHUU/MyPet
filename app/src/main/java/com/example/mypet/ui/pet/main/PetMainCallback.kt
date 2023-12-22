package com.example.mypet.ui.pet.main

import com.example.mypet.domain.pet.list.PetListMainModel

interface PetMainCallback {
    fun onClickPetAdd()
    fun onClickPet(petListMainModel: PetListMainModel?)
    fun onClickPetDelete(petListMainModel: PetListMainModel)
    fun onClickPetEdit(petListMainModel: PetListMainModel)
}