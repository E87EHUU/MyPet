package com.example.mypet.ui.pet.list

import com.example.mypet.domain.pet.detail.PetModel

interface OnPetClickListener {
    fun onPetClick(pet: PetModel)
}