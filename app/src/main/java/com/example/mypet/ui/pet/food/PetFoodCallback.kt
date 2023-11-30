package com.example.mypet.ui.pet.food

import com.example.mypet.domain.pet.food.PetFoodAlarmModel

interface PetFoodCallback {
    fun onPetFoodAlarmClick(petFoodAlarmModel: PetFoodAlarmModel)
}