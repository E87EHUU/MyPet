package com.example.mypet.domain

import com.example.mypet.domain.food.CareModel
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun getFoodModels(petMyId: Int): Flow<List<CareModel>>
}