package com.example.mypet.ui.pet.food

import com.example.mypet.domain.pet.PetFoodAlarmModel

interface PetFoodAdapterCallback {
    fun onItemClick(petFoodModel: PetFoodAlarmModel)
}