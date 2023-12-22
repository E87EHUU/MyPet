package com.example.mypet.ui.pet.main.list

import com.example.mypet.domain.pet.list.PetListMainModel

interface PetListCallback {
    fun onClickPetAdd()
    fun onClickPet(petListMainModel: PetListMainModel?)
}