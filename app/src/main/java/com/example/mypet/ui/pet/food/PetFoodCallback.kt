package com.example.mypet.ui.pet.food

import com.example.mypet.domain.alarm.AlarmMinModel
import com.example.mypet.domain.pet.care.PetCareModel

interface PetFoodCallback {
    fun onClickPetFood(petCareModel: PetCareModel)
    fun onClickAlarm(alarmMinModel: AlarmMinModel)
}