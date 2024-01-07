package com.example.mypet.domain.pet.food

import com.example.mypet.domain.pet.care.PetCareModel

data class PetFoodModel(
    val care: PetCareModel,
    val alarms: List<PetFoodAlarmModel>
)