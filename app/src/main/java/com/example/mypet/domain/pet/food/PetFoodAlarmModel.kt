package com.example.mypet.domain.pet.food

data class PetFoodAlarmModel(
    val id: Int,
    val hour: Int,
    val minute: Int,
    val isActive: Boolean,
)