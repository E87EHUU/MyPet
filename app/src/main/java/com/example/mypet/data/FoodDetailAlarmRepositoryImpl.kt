package com.example.mypet.data

import android.net.Uri
import com.example.mypet.data.alarm.IAlarmDao
import com.example.mypet.data.local.room.dao.LocalFoodDetailAlarmDao
import com.example.mypet.data.local.room.model.pet.LocalFoodAlarmModel
import com.example.mypet.domain.FoodDetailAlarmRepository
import com.example.mypet.domain.food.detail.alarm.FoodAlarmModel
import com.example.mypet.domain.food.detail.alarm.SaveFoodDetailAlarmAndSetAlarm
import javax.inject.Inject

class FoodDetailAlarmRepositoryImpl @Inject constructor(
    private val localFoodDetailAlarmDao: LocalFoodDetailAlarmDao,
    private val alarmDao: IAlarmDao,
) : FoodDetailAlarmRepository {
    override suspend fun getFoodAlarmModel(foodMyId: Int) =
        localFoodDetailAlarmDao.getLocalFoodAlarmModelByFoodId(foodMyId)
            ?.toFoodAlarmModel()

    override suspend fun saveAndSetFoodDetailAlarm(saveFoodDetailAlarmAndSetAlarm: SaveFoodDetailAlarmAndSetAlarm) {
        localFoodDetailAlarmDao.savePetFoodAndAlarm(saveFoodDetailAlarmAndSetAlarm)
            .toAlarmModel()?.let { alarmDao.setAlarm(it) }
    }

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