package com.example.mypet.domain

import com.example.mypet.domain.food.detail.alarm.FoodAlarmModel

interface FoodAlarmServiceRepository {
    suspend fun getFoodAlarmModel(alarmId: Int): FoodAlarmModel?
    suspend fun stopFoodAlarm(alarmId: Int)
    suspend fun setAlarm(foodAlarmModel: FoodAlarmModel)
}