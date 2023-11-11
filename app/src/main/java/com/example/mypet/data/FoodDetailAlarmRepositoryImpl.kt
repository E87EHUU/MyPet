package com.example.mypet.data

import android.net.Uri
import com.example.mypet.data.alarm.AlarmModel
import com.example.mypet.data.alarm.IAlarmDao
import com.example.mypet.data.local.room.dao.LocalFoodDetailAlarmDao
import com.example.mypet.data.local.room.model.pet.LocalFoodDetailAlarmModel
import com.example.mypet.domain.FoodDetailAlarmRepository
import com.example.mypet.domain.food.detail.alarm.FoodDetailAlarmModel
import com.example.mypet.domain.food.detail.alarm.SaveFoodDetailAlarmAndSetAlarm
import javax.inject.Inject

class FoodDetailAlarmRepositoryImpl @Inject constructor(
    private val localFoodDetailAlarmDao: LocalFoodDetailAlarmDao,
    private val alarmDao: IAlarmDao,
) : FoodDetailAlarmRepository {
    override suspend fun getFoodDetailAlarmModel(foodMyId: Int) =
        localFoodDetailAlarmDao.getLocalFoodDetailAlarmModelByFoodId(foodMyId)
            ?.toFoodDetailAlarmModel()

    override suspend fun saveAndSetFoodDetailAlarm(saveFoodDetailAlarmAndSetAlarm: SaveFoodDetailAlarmAndSetAlarm) {
        localFoodDetailAlarmDao.savePetFoodAndAlarm(saveFoodDetailAlarmAndSetAlarm)
            .toAlarmModel()?.let { alarmDao.setAlarm(it) }
    }

    override suspend fun stopFoodDetailAlarm(alarmId: Int) {
        localFoodDetailAlarmDao.getLocalFoodDetailAlarmModelByAlarmId(alarmId)
            ?.let { foodAlarmModel ->
                with(foodAlarmModel) {
                    alarmDao.setAlarm(toAlarmModel())
                }
            }
    }

    private fun LocalFoodDetailAlarmModel.toAlarmModel() =
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

    private fun LocalFoodDetailAlarmModel.toFoodDetailAlarmModel() =
        FoodDetailAlarmModel(
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