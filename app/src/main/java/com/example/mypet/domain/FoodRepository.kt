package com.example.mypet.domain

import com.example.mypet.domain.food.FoodModel
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun getFoodModels(petMyId: Int): Flow<List<FoodModel>>
}