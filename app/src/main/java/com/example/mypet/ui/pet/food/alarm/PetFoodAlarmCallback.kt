package com.example.mypet.ui.pet.food.alarm

import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.food.PetFoodAlarmModel

interface PetFoodAlarmCallback {
    fun onClickPetFood(petCareModel: PetCareModel)
    fun onClickPetFoodAlarm(petFoodAlarmModel: PetFoodAlarmModel)
}