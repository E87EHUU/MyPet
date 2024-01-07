package com.example.mypet.data

import com.example.mypet.data.alarm.AlarmCalculator
import com.example.mypet.data.alarm.IAlarmDao
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.dao.LocalCareDao
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalCareEntity
import com.example.mypet.data.local.room.entity.LocalEndEntity
import com.example.mypet.data.local.room.entity.LocalEndEntity.Companion.DEFAULT_COUNTER
import com.example.mypet.data.local.room.entity.LocalRepeatEntity
import com.example.mypet.data.local.room.entity.LocalStartEntity
import com.example.mypet.domain.CareRepository
import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.CareEndModel
import com.example.mypet.domain.care.CareMainModel
import com.example.mypet.domain.care.CareModel
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.CareStartModel
import com.example.mypet.domain.care.CareTypes
import com.example.mypet.domain.care.alarm.CareAlarmDetailMainModel
import com.example.mypet.domain.care.end.CareEndTypes
import com.example.mypet.domain.care.repeat.CareRepeatInterval
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.util.Calendar
import javax.inject.Inject


class CareRepositoryImpl @Inject constructor(
    private val localCareDao: LocalCareDao,
    private val alarmDao: IAlarmDao,
) : CareRepository {
    override suspend fun getCareMainModel(petId: Int, careTypeOrdinal: Int) =
        flow {
            val localCareEntity = localCareDao.getLocalCareEntity(petId, careTypeOrdinal)

            val careMainModel = CareMainModel(
                id = localCareEntity?.id ?: DEFAULT_ID,
                petId = petId,
                careType = CareTypes.entries[localCareEntity?.careTypeOrdinal ?: careTypeOrdinal],
                title = localCareEntity?.title,
                note = localCareEntity?.note,
                dose = localCareEntity?.dose,
            )

            emit(careMainModel)
        }

    override suspend fun getCareStartModel(careId: Int, careTypeOrdinal: Int) =
        flow {
            when (careTypeOrdinal) {
                CareTypes.FOOD.ordinal,
                CareTypes.BATH.ordinal,
                CareTypes.WALK.ordinal -> emit(null)

                else -> {
                    val localStartEntity = localCareDao.getLocalStartEntity(careId)

                    val careStartModel = CareStartModel(
                        id = localStartEntity?.id ?: DEFAULT_ID,
                        timeInMillis = localStartEntity?.timeInMillis
                            ?: Calendar.getInstance().timeInMillis,
                        hour = localStartEntity?.hour
                            ?: LocalDateTime.now().hour,
                        minute = localStartEntity?.minute
                            ?: LocalDateTime.now().minute
                    )
                    emit(careStartModel)
                }
            }
        }

    override suspend fun getCareRepeatModel(careId: Int, careTypeOrdinal: Int) =
        flow {
            val localRepeatEntity = localCareDao.getLocalRepeatEntity(careId)

            val careRepeatModel = CareRepeatModel(
                id = localRepeatEntity?.id ?: DEFAULT_ID,
                intervalTimes = getDefaultIntervalTimes(careTypeOrdinal),
                intervalOrdinal = getDefaultIntervalOrdinal(careTypeOrdinal),
                isMonday = localRepeatEntity?.isMonday ?: false,
                isTuesday = localRepeatEntity?.isTuesday ?: false,
                isWednesday = localRepeatEntity?.isWednesday ?: false,
                isThursday = localRepeatEntity?.isThursday ?: false,
                isFriday = localRepeatEntity?.isFriday ?: false,
                isSaturday = localRepeatEntity?.isSaturday ?: false,
                isSunday = localRepeatEntity?.isSunday ?: false,
            )
            emit(careRepeatModel)
        }

    override suspend fun getCareEndModel(careId: Int, careTypeOrdinal: Int) =
        flow {
            val localRepeatEntity = localCareDao.getLocalEndEntity(careId)

            val careRepeatModel = CareEndModel(
                id = localRepeatEntity?.id ?: DEFAULT_ID,
                counter = localRepeatEntity?.counter ?: DEFAULT_COUNTER,
                typeOrdinal = localRepeatEntity?.typeOrdinal?.let { CareEndTypes.entries[it].ordinal },
                afterTimes = localRepeatEntity?.afterTimes,
                afterDate = localRepeatEntity?.afterDate,
            )
            emit(careRepeatModel)
        }

    override suspend fun getCareAlarmModel(careId: Int, careTypeOrdinal: Int) =
        flow {
            val careAlarmModels = localCareDao.getLocalAlarmEntities(careId)
                .map { it.toCareAlarmDetailModel() }

            val careAlarmModel = CareAlarmModel(alarms = careAlarmModels.toMutableList())
            emit(careAlarmModel)
        }

    override suspend fun saveCareModels(careModels: List<CareModel>) =
        flow {
            var careId: Int? = null
            var localStartEntity: LocalStartEntity? = null
            var localRepeatEntity: LocalRepeatEntity? = null
            var localEndEntity: LocalEndEntity? = null

            careModels.forEach { careModel ->
                when (careModel) {
                    is CareMainModel ->
                        careId = careModel.save().toInt()

                    is CareStartModel ->
                        careId?.let {
                            localStartEntity = careModel.toLocalStartEntity(it)
                            localCareDao.saveLocalStartEntity(localStartEntity!!)
                        }

                    is CareRepeatModel ->
                        careId?.let {
                            localRepeatEntity = careModel.toLocalRepeatEntity(it)
                            localCareDao.saveLocalRepeatEntity(localRepeatEntity!!)
                        }

                    is CareEndModel ->
                        careId?.let {
                            localEndEntity = careModel.toLocalEndEntity(it)
                            localCareDao.saveLocalEndEntity(localEndEntity!!)
                        }

                    is CareAlarmModel ->
                        careId?.let { careId ->
                            careModel.deletedAlarmIds.forEach {
                                alarmDao.removeAlarm(it)
                            }

                            localCareDao.deleteLocalAlarmEntities(careModel.deletedAlarmIds)

                            val alarmNextStartCalculate =
                                AlarmCalculator(
                                    localStartEntity,
                                    localRepeatEntity,
                                    localEndEntity
                                )

                            careModel.alarms.forEach { careAlarmDetailModel ->
                                when (careAlarmDetailModel) {
                                    is CareAlarmDetailMainModel -> {
                                        val localAlarmEntity = alarmNextStartCalculate
                                                .calculate(
                                                    careAlarmDetailModel.toLocalAlarmEntity(careId)
                                                )

                                        val alarmId =
                                            localCareDao.saveLocalAlarmEntity(localAlarmEntity)
                                                .toInt()

                                        localAlarmEntity.nextStart?.let {
                                            alarmDao.setAlarm(
                                                alarmId,
                                                localAlarmEntity.nextStart
                                            )
                                        }
                                    }

                                    else -> {}
                                }
                            }
                        }
                }
            }

            emit(Unit)
        }

    private fun getDefaultIntervalTimes(careTypeOrdinal: Int) =
        when (CareTypes.entries[careTypeOrdinal]) {
            CareTypes.FOOD,
            CareTypes.WALK -> 1

            else -> null
        }

    private fun getDefaultIntervalOrdinal(careTypeOrdinal: Int) =
        when (CareTypes.entries[careTypeOrdinal]) {
            CareTypes.FOOD,
            CareTypes.WALK -> CareRepeatInterval.DAY.ordinal

            else -> null
        }

    private fun CareMainModel.save() =
        localCareDao.saveLocalCareEntity(toLocalCareEntity())

    private fun CareMainModel.toLocalCareEntity() =
        LocalCareEntity(
            id = id,
            petId = petId,
            careTypeOrdinal = careType.ordinal,
            title = title,
            note = note,
            dose = dose,
        )

    private fun CareStartModel.toLocalStartEntity(careId: Int) =
        LocalStartEntity(
            id = id,
            careId = careId,
            timeInMillis = timeInMillis,
            hour = hour,
            minute = minute
        )

    private fun CareRepeatModel.toLocalRepeatEntity(careId: Int) =
        LocalRepeatEntity(
            id = id,
            careId = careId,
            intervalOrdinal = intervalOrdinal,
            intervalTimes = intervalTimes,
            isMonday,
            isTuesday,
            isWednesday,
            isThursday,
            isFriday,
            isSaturday,
            isSunday,
        )

    private fun CareEndModel.toLocalEndEntity(careId: Int) =
        LocalEndEntity(
            id = id,
            careId = careId,
            counter = DEFAULT_COUNTER,
            typeOrdinal = typeOrdinal,
            afterTimes = afterTimes,
            afterDate = afterDate
        )

    private fun CareAlarmDetailMainModel.toLocalAlarmEntity(careId: Int) =
        LocalAlarmEntity(
            id = id,
            careId = careId,
            beforeStart = null,
            nextStart = null,
            description = description,
            hour = hour,
            minute = minute,
            ringtonePath = ringtonePath,
            isVibration = isVibration,
            isDelay = isDelay,
            isActive = isActive
        )

    private fun LocalAlarmEntity.toCareAlarmDetailModel() =
        CareAlarmDetailMainModel(
            id = id,
            hour = hour,
            minute = minute,
            description = description,
            ringtonePath = ringtonePath,
            isVibration = isVibration ?: true,
            isDelay = isDelay ?: false,
            isActive = isActive
        )
}
