package com.example.mypet.data

import com.example.mypet.data.local.room.dao.LocalFoodDao
import com.example.mypet.data.local.room.model.food.LocalFoodAlarmModel
import com.example.mypet.domain.FoodRepository
import com.example.mypet.domain.food.CareAlarmModel
import javax.inject.Inject


class FoodRepositoryImpl @Inject constructor(
    private val localFoodDao: LocalFoodDao,
) : FoodRepository {
    override suspend fun getFoodModels(petMyId: Int) = TODO()
/*        localFoodDao.observeFoodModels(petMyId)
            .map { locals -> locals.map { it.toFoodAlarmModel() } }*/

    private fun LocalFoodAlarmModel.toFoodAlarmModel() =
        CareAlarmModel(
            alarmId,
            alarmHour,
            alarmMinute,
            alarmIsActive
        )
}
