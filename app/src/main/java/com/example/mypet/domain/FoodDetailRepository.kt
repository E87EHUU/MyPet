package com.example.mypet.domain

import com.example.mypet.domain.food.detail.FoodDetailModel

interface FoodDetailRepository {
    suspend fun getFoodDetailModel(foodId: Int): FoodDetailModel?
    suspend fun saveAndSetFoodDetailModel(foodDetailModel: FoodDetailModel)
}