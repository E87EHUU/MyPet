package com.example.mypet.data

import com.example.mypet.domain.FoodDetailRepository
import com.example.mypet.domain.food.detail.FoodDetailModel

class FoodDetailRepositoryImpl : FoodDetailRepository {
    override suspend fun getFoodDetailModel(petFoodId: Int): FoodDetailModel? {
        TODO("Not yet implemented")
    }

    override suspend fun saveAndSetFoodDetailModel(foodDetailModel: FoodDetailModel) {
        TODO("Not yet implemented")
    }
}