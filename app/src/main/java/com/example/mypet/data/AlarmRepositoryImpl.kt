package com.example.mypet.data

import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.local.room.dao.LocalAlarmDao
import com.example.mypet.domain.AlarmRepository
import com.example.mypet.domain.alarm.AlarmSwitchModel
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val localAlarmDao: LocalAlarmDao,
    private val alarmDao: AlarmDao,
) : AlarmRepository {
    override suspend fun switch(alarmSwitchModel: AlarmSwitchModel) {
        with(alarmSwitchModel) {
/*            val id = foodId ?: careId ?: return

            if (alertIsActive) {
                val alarmModel = AlarmModel(id, alarmHour, alarmMinute)
                alarmDao.setAlarm(alarmModel)
                localAlarmDao.switchAlarmState(alarmId, true)
                return
            }

            alarmDao.removeAlarm(alarmId)
            localAlarmDao.switchAlarmState(alarmId, false)*/
        }
    }
}