package com.example.mypet.domain

import com.example.mypet.domain.food.detail.alarm.FoodAlarmModel
import com.example.mypet.domain.food.detail.alarm.SaveFoodDetailAlarmAndSetAlarm

interface FoodDetailAlarmRepository {
    suspend fun getFoodAlarmModel(foodMyId: Int): FoodAlarmModel?
    suspend fun saveAndSetFoodDetailAlarm(saveFoodDetailAlarmAndSetAlarm: SaveFoodDetailAlarmAndSetAlarm)
}