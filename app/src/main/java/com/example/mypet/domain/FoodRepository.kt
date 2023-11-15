package com.example.mypet.domain

import com.example.mypet.domain.food.FoodModel
import com.example.mypet.domain.food.SwitchFoodAlarmModel
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun observeFoodModels(petMyId: Int): Flow<List<FoodModel>>
    suspend fun switchFoodAlarm(switchPetFoodAlarmStateModel: SwitchFoodAlarmModel)
}