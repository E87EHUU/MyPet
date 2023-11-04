package com.example.mypet.data

import android.net.Uri
import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.local.room.dao.LocalFoodDetailAlarmDao
import com.example.mypet.data.local.room.model.pet.LocalFoodDetailAlarmModel
import com.example.mypet.domain.FoodDetailAlarmRepository
import com.example.mypet.domain.food.detail.alarm.FoodDetailAlarmModel
import com.example.mypet.domain.food.detail.alarm.SaveFoodDetailAlarmAndSetAlarm
import javax.inject.Inject

class FoodDetailAlarmRepositoryImpl @Inject constructor(
    private val localFoodDetailAlarmDao: LocalFoodDetailAlarmDao,
    private val alarmDao: AlarmDao,
) : FoodDetailAlarmRepository {
    override suspend fun getFoodDetailAlarmModel(foodMyId: Int) =
        localFoodDetailAlarmDao.getLocalFoodDetailAlarmModel(foodMyId)
            ?.toFoodDetailAlarmModel()

    override suspend fun saveAndSetFoodDetailAlarm(saveFoodDetailAlarmAndSetAlarm: SaveFoodDetailAlarmAndSetAlarm) {
        localFoodDetailAlarmDao.savePetFoodAndAlarm(saveFoodDetailAlarmAndSetAlarm)
            .toAlarmModel()?.let { alarmDao.setAlarm(it) }
    }

    private fun LocalFoodDetailAlarmModel.toFoodDetailAlarmModel() =
        FoodDetailAlarmModel(
            id,
            name,
            alarmId,
            alarmHour,
            alarmMinute,
            alarmRepeatMonday,
            alarmRepeatTuesday,
            alarmRepeatWednesday,
            alarmRepeatThursday,
            alarmRepeatFriday,
            alarmRepeatSaturday,
            alarmRepeatSunday,
            alarmMelodyURI = alarmMelodyURI?.let { Uri.parse(alarmMelodyURI) },
            alarmIsVibration,
            alarmIsDelay,
            alarmIsActive
        )
}