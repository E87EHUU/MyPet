package com.example.mypet.ui.pet.main.list

import com.example.mypet.domain.pet.PetModel

interface PetListCallback {
    fun onPetListAddClick()
    fun onPetListMainClick(petListModel: PetModel)
}