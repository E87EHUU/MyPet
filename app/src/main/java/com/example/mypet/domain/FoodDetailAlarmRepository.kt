package com.example.mypet.domain

import com.example.mypet.domain.food.detail.alarm.FoodDetailAlarmModel
import com.example.mypet.domain.food.detail.alarm.SaveFoodDetailAlarmAndSetAlarm

interface FoodDetailAlarmRepository {
    suspend fun getFoodDetailAlarmModel(foodMyId: Int): FoodDetailAlarmModel?
    suspend fun saveAndSetFoodDetailAlarm(saveFoodDetailAlarmAndSetAlarm: SaveFoodDetailAlarmAndSetAlarm)
}