package com.example.mypet.data

import androidx.room.Transaction
import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.alarm.AlarmNextStartCalculate
import com.example.mypet.data.local.room.dao.LocalAlarmServiceDao
import com.example.mypet.data.local.room.model.alarm.LocalAlarmServiceModel
import com.example.mypet.domain.AlarmServiceRepository
import com.example.mypet.domain.alarm.service.AlarmServiceModel
import java.util.Calendar
import javax.inject.Inject

class AlarmServiceRepositoryImpl @Inject constructor(
    private val localAlarmServiceDao: LocalAlarmServiceDao,
    private val alarmDao: AlarmDao
) : AlarmServiceRepository {
    override suspend fun getAlarmServiceModel(alarmId: Int) =
        localAlarmServiceDao.getLocalAlarmServiceModel(alarmId)?.toAlarmServiceModel()

    @Transaction
    override suspend fun stopAlarm(alarmId: Int) {
        localAlarmServiceDao.getLocalAlarmEntity(alarmId)?.let { localAlarmEntity ->
            var nextStart: Long? = null

            if (localAlarmEntity.isActive) {
                localAlarmServiceDao.getLocalRepeatEntity(alarmId)?.let { localRepeatEntity ->
                    val localStartEntity = localAlarmServiceDao.getLocalStartEntity(alarmId)
                    nextStart = AlarmNextStartCalculate().getNextStartTimeInMillis(
                        localStartEntity,
                        localRepeatEntity,
                        localAlarmEntity.hour,
                        localAlarmEntity.minute
                    )
                }
            }

            nextStart?.let {
                localAlarmServiceDao.updateLocalAlarmEntity(
                    localAlarmEntity.copy(
                        nextStart = it,
                        isActive = true
                    )
                )
                alarmDao.setAlarm(localAlarmEntity.id, it)
            } ?: run {
                localAlarmServiceDao.updateLocalAlarmEntity(
                    localAlarmEntity.copy(
                        nextStart = null,
                        isActive = false
                    )
                )
            }
        }
    }

    override suspend fun setDelayAlarm(alarmId: Int) {
        val calendar = Calendar.getInstance()
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        calendar.add(Calendar.MINUTE, 5)
        alarmDao.setAlarm(alarmId, calendar.timeInMillis)
    }

    private fun LocalAlarmServiceModel.toAlarmServiceModel() =
        AlarmServiceModel(
            petId,
            petAvatarPath,
            petKindOrdinal,
            petBreedOrdinal,
            alarmId,
            alarmDescription,
            alarmHour,
            alarmMinute,
            alarmRingtonePath,
            alarmIsVibration,
            alarmIsDelay,
            alarmIsActive
        )
}
