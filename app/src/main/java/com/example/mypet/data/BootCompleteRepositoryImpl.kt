package com.example.mypet.data

import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.local.room.dao.LocalBootCompleteDao
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
                localBootCompleteDao.getActiveAlarmModels().forEach { localAlarmEntity ->
                    if (localAlarmEntity.isActive) {
                        localAlarmEntity.nextStart?.let {
                            alarmDao.setAlarm(localAlarmEntity.id, localAlarmEntity.nextStart)
                        }
                    }
                }
            }
        }
    }
}