package com.example.mypet.data

import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.local.room.dao.LocalAlarmServiceDao
import com.example.mypet.data.local.room.model.alarm.LocalAlarmServiceModel
import com.example.mypet.domain.AlarmServiceRepository
import javax.inject.Inject

class AlarmServiceRepositoryImpl @Inject constructor(
    private val localAlarmServiceDao: LocalAlarmServiceDao,
    private val alarmDao: AlarmDao
) : AlarmServiceRepository {
    override suspend fun getAlarmServiceModelByFoodId(foodId: Int) =
        localAlarmServiceDao.getLocalAlarmServiceModelByFoodId(foodId)

    override suspend fun stopAlarm(localAlarmServiceModel: LocalAlarmServiceModel) {
        val alarmModel = localAlarmServiceModel.toAlarmModel()
        if (alarmModel.isRepeatable()) alarmDao.setAlarm(alarmModel)
        else localAlarmServiceDao.disableAlarm(localAlarmServiceModel.alarmId)
    }

    override suspend fun setDelayAlarm(localAlarmServiceModel: LocalAlarmServiceModel) {
        alarmDao.setAlarm(localAlarmServiceModel.toAlarmModel(1))
    }
}
