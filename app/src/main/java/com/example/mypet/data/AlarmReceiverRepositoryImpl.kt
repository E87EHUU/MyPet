package com.example.mypet.data

import com.example.mypet.data.local.room.dao.LocalAlarmReceiverDao
import com.example.mypet.data.local.room.model.alarm.LocalAlarmReceiverModel
import com.example.mypet.domain.AlarmReceiverRepository
import com.example.mypet.domain.alarm.receiver.AlarmReceiverModel
import javax.inject.Inject

class AlarmReceiverRepositoryImpl @Inject constructor(
    private val localAlarmReceiverDao: LocalAlarmReceiverDao,
) : AlarmReceiverRepository {
    override suspend fun getAlarmReceiverModel(alarmId: Int) =
        localAlarmReceiverDao.getLocalAlarmReceiverModel(alarmId)?.toAlarmReceiverModel()

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
