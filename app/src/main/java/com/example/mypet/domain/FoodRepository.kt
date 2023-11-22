package com.example.mypet.domain

import com.example.mypet.domain.food.FoodModel
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun observeFoodModels(petMyId: Int): Flow<List<FoodModel>>
}