package com.example.mypet.ui.pet.detail.food

import com.example.mypet.domain.pet.detail.PetFoodModel

interface PetDetailFoodAdapterCallback {
    fun onItemClick(petFoodModel: PetFoodModel)
    fun onSwitchActive(petFoodModel: PetFoodModel)
}