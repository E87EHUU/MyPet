package com.example.mypet.data

import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.local.room.dao.LocalFoodDetailDao
import com.example.mypet.domain.FoodDetailRepository
import com.example.mypet.domain.food.detail.FoodDetailModel
import javax.inject.Inject

class FoodDetailRepositoryImpl @Inject constructor(
    private val localFoodDetailDao: LocalFoodDetailDao,
    private val alarmDao: AlarmDao,
) : FoodDetailRepository {
    override suspend fun getFoodDetailModel(foodId: Int) =
        localFoodDetailDao.getLocalFoodDetailModel(foodId)

    override suspend fun saveAndSetFoodDetailModel(foodDetailModel: FoodDetailModel) {
        localFoodDetailDao.saveFoodDetailModel(foodDetailModel)
            ?.toAlarmModel()?.let { alarmDao.setAlarm(it) }
    }
}