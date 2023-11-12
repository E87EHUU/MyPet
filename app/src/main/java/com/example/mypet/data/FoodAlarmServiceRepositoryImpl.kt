package com.example.mypet.data

import android.net.Uri
import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.alarm.AlarmModel
import com.example.mypet.data.local.room.dao.LocalFoodAlarmServiceDao
import com.example.mypet.data.local.room.model.pet.LocalFoodAlarmModel
import com.example.mypet.domain.FoodAlarmServiceRepository
import com.example.mypet.domain.food.detail.alarm.FoodAlarmModel
import javax.inject.Inject

class FoodAlarmServiceRepositoryImpl @Inject constructor(
    private val localFoodAlarmServiceDao: LocalFoodAlarmServiceDao,
    private val alarmDao: AlarmDao
) : FoodAlarmServiceRepository {
    override suspend fun getFoodAlarmModel(alarmId: Int) =
        localFoodAlarmServiceDao.getLocalFoodAlarmModelByAlarmId(alarmId)
            ?.toFoodAlarmModel()

    override suspend fun stopFoodAlarm(alarmId: Int) {
        localFoodAlarmServiceDao.getLocalFoodAlarmModelByAlarmId(alarmId)
            ?.let { foodAlarmModel ->
                with(foodAlarmModel) {
                    alarmDao.setAlarm(toAlarmModel())
                }
            }
    }

    private fun LocalFoodAlarmModel.toAlarmModel() =
        AlarmModel(
            alarmId,
            hour,
            minute,
            isRepeatMonday,
            isRepeatTuesday,
            isRepeatWednesday,
            isRepeatThursday,
            isRepeatFriday,
            isRepeatSaturday,
            isRepeatSunday
        )

    private fun LocalFoodAlarmModel.toFoodAlarmModel() =
        FoodAlarmModel(
            foodId,
            title,
            iconResId,
            alarmId,
            hour,
            minute,
            isRepeatMonday,
            isRepeatTuesday,
            isRepeatWednesday,
            isRepeatThursday,
            isRepeatFriday,
            isRepeatSaturday,
            isRepeatSunday,
            ringtoneUri = ringtoneUri?.let { Uri.parse(ringtoneUri) },
            isVibration,
            isDelay,
            isActive
        )
}