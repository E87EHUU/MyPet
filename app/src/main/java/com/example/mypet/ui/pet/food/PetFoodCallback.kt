package com.example.mypet.ui.pet.food

import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.food.PetFoodAlarmModel

interface PetFoodCallback {
    fun onPetFoodClick(petCareModel: PetCareModel)
    fun onPetFoodAlarmClick(petFoodAlarmModel: PetFoodAlarmModel)
}