package com.example.mypet.ui.pet.main.list

import com.example.mypet.domain.pet.list.PetListModel

interface PetListCallback {
    fun onPetListAddClick()
    fun onPetListMainClick(petListModel: PetListModel)
}