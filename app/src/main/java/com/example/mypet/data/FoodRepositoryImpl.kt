package com.example.mypet.data

import com.example.mypet.data.local.room.dao.LocalFoodDao
import com.example.mypet.domain.FoodRepository
import javax.inject.Inject


class FoodRepositoryImpl @Inject constructor(
    private val localFoodDao: LocalFoodDao,
) : FoodRepository {
    override suspend fun getFoodModels(petMyId: Int) =
        localFoodDao.observeFoodModels(petMyId)
}
