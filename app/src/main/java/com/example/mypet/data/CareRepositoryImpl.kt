package com.example.mypet.data

import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.dao.LocalCareDao
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.domain.CareRepository
import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.CareMainModel
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.CareStartModel
import com.example.mypet.domain.care.CareTypes
import com.example.mypet.domain.care.alarm.CareAlarmDetailModel
import com.example.mypet.domain.care.repeat.CareRepeatEndTypes
import com.example.mypet.domain.care.repeat.CareRepeatInterval
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.util.Calendar
import javax.inject.Inject


class CareRepositoryImpl @Inject constructor(
    private val localCareDao: LocalCareDao,
) : CareRepository {
    override suspend fun getCareMainModel(careId: Int, careTypeOrdinal: Int) =
        flow {
            val localCareEntity = localCareDao.getLocalCareEntity(careId)

            val careMainModel = CareMainModel(
                careType = CareTypes.values()[localCareEntity?.careTypeOrdinal ?: careTypeOrdinal],
                note = localCareEntity?.note,
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
                        intervalOrdinal = localRepeatEntity?.intervalOrdinal
                            ?: CareRepeatInterval.DAY.ordinal,
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
            val careAlarmModel = CareAlarmModel(
                alarms = localCareDao.getLocalAlarmEntities(careId)
                    .map { it.toCareAlarmDetailModel() }
            )
            emit(careAlarmModel)
        }

    private fun LocalAlarmEntity.toCareAlarmDetailModel() =
        CareAlarmDetailModel(
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
