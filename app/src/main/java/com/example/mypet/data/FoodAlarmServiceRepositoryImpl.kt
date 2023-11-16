package com.example.mypet.data

import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.alarm.AlarmModel
import com.example.mypet.data.alarm.isRepeatable
import com.example.mypet.data.local.room.dao.LocalFoodAlarmServiceDao
import com.example.mypet.data.local.room.model.pet.LocalFoodAlarmModel
import com.example.mypet.domain.FoodAlarmServiceRepository
import com.example.mypet.domain.food.alarm.FoodAlarmModel
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
            ?.let {
                val alarmModel = it.toAlarmModel()
                if (alarmModel.isRepeatable()) alarmDao.setAlarm(alarmModel)
                else localFoodAlarmServiceDao.disableAlarm(alarmId)
            }
    }

    override suspend fun setAlarm(foodAlarmModel: FoodAlarmModel) {
        alarmDao.setAlarm(foodAlarmModel.toDelayAlarmModel())
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
            foodTitle,
            null,
            petKindId,
            petBreedId,
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
            ringtonePath = ringtonePath,
            isVibration,
            isDelay,
            isActive
        )
}