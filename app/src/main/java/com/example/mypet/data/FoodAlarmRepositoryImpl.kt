package com.example.mypet.data

import com.example.mypet.data.alarm.IAlarmDao
import com.example.mypet.data.local.room.dao.LocalFoodAlarmDao
import com.example.mypet.data.local.room.model.pet.LocalFoodAlarmModel
import com.example.mypet.domain.FoodAlarmRepository
import com.example.mypet.domain.food.SaveAndSetFoodAlarmModel
import com.example.mypet.domain.food.alarm.FoodAlarmModel
import javax.inject.Inject

class FoodAlarmRepositoryImpl @Inject constructor(
    private val localFoodDetailAlarmDao: LocalFoodAlarmDao,
    private val alarmDao: IAlarmDao,
) : FoodAlarmRepository {
    override suspend fun getFoodAlarmModel(foodMyId: Int) =
        localFoodDetailAlarmDao.getLocalFoodAlarmModelByFoodId(foodMyId)
            ?.toFoodAlarmModel()

    override suspend fun saveAndSetFoodDetailAlarm(saveFoodDetailAlarmAndSetAlarm: SaveAndSetFoodAlarmModel) {
        localFoodDetailAlarmDao.savePetFoodAndAlarm(saveFoodDetailAlarmAndSetAlarm)
            .toAlarmModel()?.let { alarmDao.setAlarm(it) }
    }

    private fun LocalFoodAlarmModel.toFoodAlarmModel() =
        FoodAlarmModel(
            foodId,
            foodTitle,
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
            ringtonePath,
            isVibration,
            isDelay,
            isActive
        )
}