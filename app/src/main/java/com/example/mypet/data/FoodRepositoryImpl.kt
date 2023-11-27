package com.example.mypet.data

import com.example.mypet.data.local.room.dao.LocalFoodDao
import com.example.mypet.data.local.room.model.food.LocalFoodAlarmModel
import com.example.mypet.domain.FoodRepository
import com.example.mypet.domain.food.CareAlarmModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class FoodRepositoryImpl @Inject constructor(
    private val localFoodDao: LocalFoodDao,
) : FoodRepository {
    override suspend fun getFoodModels(petMyId: Int) =
        localFoodDao.observeFoodModels(petMyId)
            .map { locals -> locals.map { it.toFoodAlarmModel() } }

    private fun LocalFoodAlarmModel.toFoodAlarmModel() =
        CareAlarmModel(
            foodId,
            foodTitle,
            foodRation,
            alarmId,
            alarmHour,
            alarmMinute,
            alarmIsActive
        )
}
