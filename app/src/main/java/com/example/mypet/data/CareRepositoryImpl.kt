package com.example.mypet.data

import com.example.mypet.data.alarm.AlarmNextStartCalculate
import com.example.mypet.data.alarm.IAlarmDao
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.dao.LocalCareDao
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalCareEntity
import com.example.mypet.data.local.room.entity.LocalRepeatEntity
import com.example.mypet.data.local.room.entity.LocalStartEntity
import com.example.mypet.domain.CareRepository
import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.CareMainModel
import com.example.mypet.domain.care.CareModel
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.CareStartModel
import com.example.mypet.domain.care.CareTypes
import com.example.mypet.domain.care.alarm.CareAlarmDetailMainModel
import com.example.mypet.domain.care.repeat.CareRepeatEndTypes
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
                careType = CareTypes.values()[localCareEntity?.careTypeOrdinal ?: careTypeOrdinal],
                title = localCareEntity?.title,
                note = localCareEntity?.note,
                dose = localCareEntity?.dose,
                progress = localCareEntity?.progress
            )

            emit(careMainModel)
        }

    override suspend fun getCareStartModel(careId: Int, careTypeOrdinal: Int) =
        flow {
            when (careTypeOrdinal) {
                CareTypes.FOOD.ordinal -> emit(null)
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
            when (careTypeOrdinal) {
                CareTypes.FOOD.ordinal -> emit(null)
                else -> {
                    val localRepeatEntity = localCareDao.getLocalRepeatEntity(careId)

                    val careRepeatModel = CareRepeatModel(
                        id = localRepeatEntity?.id ?: DEFAULT_ID,
                        intervalTimes = localRepeatEntity?.intervalTimes ?: "1",
                        intervalOrdinal = localRepeatEntity?.intervalOrdinal,
                        isMonday = localRepeatEntity?.isMonday ?: false,
                        isTuesday = localRepeatEntity?.isTuesday ?: false,
                        isWednesday = localRepeatEntity?.isWednesday ?: false,
                        isThursday = localRepeatEntity?.isThursday ?: false,
                        isFriday = localRepeatEntity?.isFriday ?: false,
                        isSaturday = localRepeatEntity?.isSaturday ?: false,
                        isSunday = localRepeatEntity?.isSunday ?: false,
                        endTypeOrdinal = localRepeatEntity?.endTypeOrdinal?.let { CareRepeatEndTypes.values()[it].ordinal }
                            ?: CareRepeatEndTypes.NONE.ordinal,
                        endAfterTimes = localRepeatEntity?.endAfterTimes ?: "1",
                        endAfterDate = localRepeatEntity?.endAfterDate
                            ?: Calendar.getInstance().timeInMillis
                    )
                    emit(careRepeatModel)
                }
            }
        }

    override suspend fun getCareAlarmModel(careId: Int, careTypeOrdinal: Int) =
        flow {
            val careAlarmModels = localCareDao.getLocalAlarmEntities(careId)
                .map { it.toCareAlarmDetailModel() }

            val careAlarmModel = CareAlarmModel(alarms = careAlarmModels)
            emit(careAlarmModel)
        }

    override suspend fun saveCareModels(careModels: List<CareModel>) =
        flow {
            var careId: Int? = null
            var localStartEntity: LocalStartEntity? = null
            var localRepeatEntity: LocalRepeatEntity? = null
            val alarmNextStartCalculate = AlarmNextStartCalculate()

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

                    is CareAlarmModel ->
                        careId?.let { careId ->
                            careModel.deletedAlarmIds.forEach {
                                alarmDao.removeAlarm(it)
                            }

                            localCareDao.deleteLocalAlarmEntities(careModel.deletedAlarmIds)

                            careModel.alarms.forEach { careAlarmDetailModel ->
                                when (careAlarmDetailModel) {
                                    is CareAlarmDetailMainModel -> {
                                        val nextStart = if (careAlarmDetailModel.isActive) {
                                            alarmNextStartCalculate.getNextStartTimeInMillis(
                                                localStartEntity,
                                                localRepeatEntity,
                                                careAlarmDetailModel.hour,
                                                careAlarmDetailModel.minute
                                            )
                                        } else null

                                        val localAlarmEntity =
                                            careAlarmDetailModel.toLocalAlarmEntity(
                                                careId,
                                                nextStart
                                            )
                                        localCareDao.saveLocalAlarmEntity(localAlarmEntity)

                                        nextStart?.let {
                                            alarmDao.setAlarm(careAlarmDetailModel.id, nextStart)
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
            progress = progress
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
            id,
            careId,
            intervalOrdinal,
            intervalTimes,
            isMonday,
            isTuesday,
            isWednesday,
            isThursday,
            isFriday,
            isSaturday,
            isSunday,
            endTypeOrdinal,
            endAfterTimes,
            endAfterDate
        )

    private fun CareAlarmDetailMainModel.toLocalAlarmEntity(careId: Int, nextStart: Long?) =
        LocalAlarmEntity(
            id,
            careId,
            nextStart,
            description,
            hour,
            minute,
            ringtonePath,
            isVibration,
            isDelay,
            isActive
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
