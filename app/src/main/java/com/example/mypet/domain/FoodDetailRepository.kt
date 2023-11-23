package com.example.mypet.domain

import com.example.mypet.domain.food.detail.FoodDetailModel
import com.example.mypet.domain.food.detail.SaveAndSetFoodDetailModel

interface FoodDetailRepository {
    suspend fun getFoodDetailModel(foodId: Int): FoodDetailModel?
    suspend fun saveAndSetFoodDetailModel(saveAndSetFoodDetailModel: SaveAndSetFoodDetailModel)
}