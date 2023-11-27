package com.example.mypet.data

import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.local.room.dao.LocalAlarmServiceDao
import com.example.mypet.domain.AlarmServiceRepository
import com.example.mypet.domain.alarm.service.AlarmServiceModel
import javax.inject.Inject

class AlarmServiceRepositoryImpl @Inject constructor(
    private val localAlarmServiceDao: LocalAlarmServiceDao,
    private val alarmDao: AlarmDao
) : AlarmServiceRepository {
    override suspend fun getAlarmServiceModel(alarmId: Int) =
        localAlarmServiceDao.getLocalAlarmServiceModel(alarmId)

    override suspend fun stopAlarm(localAlarmServiceModel: AlarmServiceModel) {
        val alarmModel = localAlarmServiceModel.toAlarmModel()
        if (alarmModel.isRepeatable()) alarmDao.setAlarm(alarmModel)
        else localAlarmServiceDao.disableAlarm(localAlarmServiceModel.alarmId)
    }

    override suspend fun setDelayAlarm(localAlarmServiceModel: AlarmServiceModel) {
        alarmDao.setAlarm(localAlarmServiceModel.toAlarmModel(1))
    }
}
