package com.example.mypet.ui.pet.food

import com.example.mypet.domain.pet.food.PetFoodModel

interface PetFoodAdapterCallback {
    fun onItemClick(petFoodModel: PetFoodModel)
}