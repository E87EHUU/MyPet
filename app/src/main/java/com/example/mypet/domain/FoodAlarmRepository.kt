package com.example.mypet.domain

import com.example.mypet.domain.food.alarm.FoodAlarmModel
import com.example.mypet.domain.food.SaveAndSetFoodAlarmModel

interface FoodAlarmRepository {
    suspend fun getFoodAlarmModel(foodMyId: Int): FoodAlarmModel?
    suspend fun saveAndSetFoodDetailAlarm(saveFoodDetailAlarmAndSetAlarm: SaveAndSetFoodAlarmModel)
}