package com.example.mypet.data

import android.net.Uri
import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.local.room.dao.LocalFoodDetailDao
import com.example.mypet.data.local.room.model.alarm.LocalFoodDetailModel
import com.example.mypet.domain.FoodDetailRepository
import com.example.mypet.domain.food.detail.FoodDetailModel
import com.example.mypet.domain.food.detail.SaveAndSetFoodDetailModel
import javax.inject.Inject

class FoodDetailRepositoryImpl @Inject constructor(
    private val localFoodDetailDao: LocalFoodDetailDao,
    private val alarmDao: AlarmDao,
) : FoodDetailRepository {
    override suspend fun getFoodDetailModel(foodId: Int) =
        localFoodDetailDao.getLocalFoodDetailModel(foodId)?.toFoodDetailModel()

    override suspend fun saveAndSetFoodDetailModel(saveAndSetFoodDetailModel: SaveAndSetFoodDetailModel) {
        localFoodDetailDao.savePetFoodAndAlarm(saveAndSetFoodDetailModel)
            .toAlarmModel()?.let { alarmDao.setAlarm(it) }
    }

    private fun LocalFoodDetailModel.toFoodDetailModel() =
        FoodDetailModel(
            foodId,
            foodTitle,
            foodRation,
            myAvatarUri = myAvatarPath?.let { Uri.parse(it) },
            kindOrdinal,
            breedOrdinal,
            alarmId,
            alarmHour,
            alarmMinute,
            alarmIsRepeatMonday,
            alarmIsRepeatTuesday,
            alarmIsRepeatWednesday,
            alarmIsRepeatThursday,
            alarmIsRepeatFriday,
            alarmIsRepeatSaturday,
            alarmIsRepeatSunday,
            alarmRingtonePath,
            alarmIsVibration,
            alarmIsDelay,
            alarmIsActive
        )
}