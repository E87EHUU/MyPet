package com.example.mypet.data

import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.alarm.AlarmModel
import com.example.mypet.data.local.room.dao.LocalFoodDao
import com.example.mypet.data.local.room.model.pet.LocalFoodAlarmModel
import com.example.mypet.data.local.room.model.pet.LocalFoodModel
import com.example.mypet.domain.FoodRepository
import com.example.mypet.domain.food.FoodModel
import com.example.mypet.domain.food.SwitchFoodAlarmModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class FoodRepositoryImpl @Inject constructor(
    private val localFoodDao: LocalFoodDao,
    private val alarmDao: AlarmDao,
) : FoodRepository {
    private fun LocalFoodAlarmModel.toAlarmModel() =
        AlarmModel(alarmId, hour, minute)

    override suspend fun observeFoodModels(petMyId: Int) =
        localFoodDao.observeFoodModels(petMyId)
            .map { localFoodModels -> localFoodModels.map { it.toFoodModel() } }

    override suspend fun switchFoodAlarm(switchPetFoodAlarmStateModel: SwitchFoodAlarmModel) {
        with(switchPetFoodAlarmStateModel) {
            if (alertIsActive) {
                localFoodDao
                    .getLocalFoodAlarmModel(alarmId)
                    ?.toAlarmModel()
                    ?.let {
                        alarmDao.setAlarm(it)
                        localFoodDao.switchAlarmState(alarmId, true)
                        return
                    }
            }

            alarmDao.removeAlarm(alarmId)
            localFoodDao.switchAlarmState(alarmId, false)
        }
    }

    private fun LocalFoodModel.toFoodModel() =
        FoodModel(
            foodId = foodId,
            foodTitle = foodTitle,
            alarmId = alarmId,
            alarmHour = alarmHour,
            alarmMinute = alarmMinute,
            alarmIsActive = alarmIsActive
        )
}