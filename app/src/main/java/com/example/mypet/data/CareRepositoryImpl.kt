package com.example.mypet.data

import com.example.mypet.data.local.room.dao.LocalCareDao
import com.example.mypet.domain.CareRepository
import com.example.mypet.domain.care.CareTypes
import com.example.mypet.domain.care.CareViewHolderFoodModel
import com.example.mypet.domain.care.CareViewHolderHeaderAlarmModel
import com.example.mypet.domain.care.CareViewHolderModel
import com.example.mypet.domain.care.CareViewHolderRepeatModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CareRepositoryImpl @Inject constructor(
    private val localCareDao: LocalCareDao,
) : CareRepository {
    override fun getCareViewHolderModels(
        careId: Int,
        careTypeOrdinal: Int
    ) =
        when (careTypeOrdinal) {
            CareTypes.FOOD.ordinal -> getCareFoodModels(careId)
            else -> flow { emit(emptyList()) }
        }

    override fun getCareViewHolderAlarmModel(careId: Int) =
        localCareDao.getLocalAlarmMinModels(careId)
            .map { its ->
                val mutableList = mutableListOf<CareViewHolderModel>()
                mutableList.add(CareViewHolderHeaderAlarmModel(""))
                mutableList.addAll(its.map { it.toCareViewHolderAlarmModel() })
                mutableList.toList()
            }

    private fun getCareFoodModels(careId: Int) =
        localCareDao.getLocalCareEntity(careId).map {
            it?.let {
                mutableListOf(
                    CareViewHolderFoodModel(""),
                    CareViewHolderRepeatModel(""),
                )
            } ?: run { emptyList() }
        }
}
