package com.example.mypet.ui.pet.food.alarm

import com.example.mypet.domain.alarm.AlarmMinModel
import com.example.mypet.domain.pet.care.PetCareModel

interface PetFoodAlarmCallback {
    fun onClickPetFood(petCareModel: PetCareModel)
    fun onClickAlarm(alarmMinModel: AlarmMinModel)
}