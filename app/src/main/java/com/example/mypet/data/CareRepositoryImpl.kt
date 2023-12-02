package com.example.mypet.data

import com.example.mypet.data.local.room.dao.LocalCareDao
import com.example.mypet.domain.CareRepository
import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.CareMainModel
import com.example.mypet.domain.care.CareTypes
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CareRepositoryImpl @Inject constructor(
    private val localCareDao: LocalCareDao,
) : CareRepository {
    override fun getCareMainModel(careId: Int, careTypeOrdinal: Int) =
        localCareDao.getLocalCareEntity(careId).map {
            CareMainModel(
                careType = CareTypes.values()[careTypeOrdinal]
            )
        }

    override fun getCareStartModel(careId: Int, careTypeOrdinal: Int) =
        flow { emit(null) }

    override fun getCareRepeatModel(careId: Int, careTypeOrdinal: Int) =
        flow { emit(null) }

    override fun getCareAlarmModel(careId: Int, careTypeOrdinal: Int) =
        localCareDao.getLocalAlarmMinModels(careId)
            .map { its ->
                CareAlarmModel(
                    alarms = its.map { it.toAlarmMinModel() }
                )
            }
}
