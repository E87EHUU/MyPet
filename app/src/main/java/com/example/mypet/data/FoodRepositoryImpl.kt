package com.example.mypet.data

import com.example.mypet.data.local.room.dao.LocalFoodDao
import com.example.mypet.domain.FoodRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class FoodRepositoryImpl @Inject constructor(
    private val localFoodDao: LocalFoodDao,
) : FoodRepository {
    override suspend fun observeFoodModels(petMyId: Int) =
        localFoodDao.observeFoodModels(petMyId)
            .map { localFoodModels -> localFoodModels.map { it.toFoodModel() } }
}
