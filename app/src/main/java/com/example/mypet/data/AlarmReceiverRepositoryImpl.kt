package com.example.mypet.data

import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.alarm.AlarmNextStartCalculate
import com.example.mypet.data.local.room.dao.LocalAlarmReceiverDao
import com.example.mypet.data.local.room.model.alarm.LocalAlarmReceiverModel
import com.example.mypet.domain.AlarmReceiverRepository
import com.example.mypet.domain.alarm.receiver.AlarmReceiverModel
import javax.inject.Inject

class AlarmReceiverRepositoryImpl @Inject constructor(
    private val localAlarmReceiverDao: LocalAlarmReceiverDao,
    private val alarmDao: AlarmDao,
) : AlarmReceiverRepository {
    override suspend fun getAlarmReceiverModel(alarmId: Int) =
        localAlarmReceiverDao.getLocalAlarmReceiverModel(alarmId)?.toAlarmReceiverModel()

    override suspend fun stopOrStartRepeatAlarm(alarmId: Int) {
        localAlarmReceiverDao.getLocalAlarmEntity(alarmId)?.let { localAlarmEntity ->
            if (localAlarmEntity.isActive) {
                localAlarmReceiverDao.getLocalRepeatEntity(alarmId)?.let { localRepeatEntity ->
                    val localStartEntity = localAlarmReceiverDao.getLocalStartEntity(alarmId)
                    val localEndEntity = localAlarmReceiverDao.getLocalEndEntity(alarmId)
                    val updatedLocalAlarmEntity =
                        AlarmNextStartCalculate().getNextStartTimeInMillis(
                            localAlarmEntity,
                            localStartEntity,
                            localRepeatEntity,
                            localEndEntity
                        )

                    localAlarmReceiverDao.updateLocalAlarmEntity(updatedLocalAlarmEntity)
                    updatedLocalAlarmEntity.nextStart?.let {
                        alarmDao.setAlarm(localAlarmEntity.id, it)
                    }

                    return
                }
            }

            localAlarmReceiverDao.updateLocalAlarmEntity(
                localAlarmEntity.copy(
                    nextStart = null,
                    isActive = false
                )
            )
        }
    }

    private fun LocalAlarmReceiverModel.toAlarmReceiverModel() =
        AlarmReceiverModel(
            petId,
            petName,
            petAvatarPath,
            petKindOrdinal,
            petBreedOrdinal,
            careTypeOrdinal,
            alarmId,
            alarmDescription,
        )
}
