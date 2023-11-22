package com.example.mypet.data

import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.domain.alarm.AlarmModel
import com.example.mypet.data.local.room.dao.LocalBootCompleteDao
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.domain.BootCompleteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class BootCompleteRepositoryImpl @Inject constructor(
    private val localBootCompleteDao: LocalBootCompleteDao,
    private val alarmDao: AlarmDao,
) : BootCompleteRepository {
    override fun setAllAlarm() {
        runBlocking {
            launch(Dispatchers.IO) {
                localBootCompleteDao.getAllAlarm().forEach { localAlarmEntity ->
                    alarmDao.setAlarm(localAlarmEntity.toAlarmModel())
                }
            }
        }
    }

    private fun LocalAlarmEntity.toAlarmModel() =
        AlarmModel(
            id,
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
}