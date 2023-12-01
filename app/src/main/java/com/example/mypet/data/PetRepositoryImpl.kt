package com.example.mypet.data

import androidx.room.Transaction
import com.example.mypet.data.local.room.LocalDatabase
import com.example.mypet.data.local.room.dao.LocalPetDao
import com.example.mypet.data.local.room.model.pet.LocalPetCareModel
import com.example.mypet.domain.PetRepository
import com.example.mypet.domain.care.CareTypes
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.food.PetFoodModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject


class PetRepositoryImpl @Inject constructor(
    private val localPetDao: LocalPetDao,
) : PetRepository {
    override fun getPet() =
        localPetDao.getLocalPetModels()
            .mapNotNull { its -> its.map { it.toPetListModel() } }

    @Transaction
    override fun getPetFoodModel(petId: Int) =
        combine(
            localPetDao.getLocalPetCareModels(petId, CareTypes.FOOD.ordinal),
            localPetDao.getLocalAlarmMinModels(petId, CareTypes.FOOD.ordinal)
        ) { localPetCareModel, localAlarmMinModels ->

            PetFoodModel(
                care = localPetCareModel.toPetCareDetailModel(CareTypes.FOOD.ordinal),
                alarmModels = localAlarmMinModels.map { it.toPetFoodAlarmModel() }
            )
        }

    override fun getCare(petId: Int) =
        flow {
            emit(emptyList<PetCareModel>())
        }

    override suspend fun deletePet(petId: Int) {
        localPetDao.deletePet(petId)
    }

    private fun LocalPetCareModel?.toPetCareDetailModel(careTypeOrdinal: Int) =
        PetCareModel(
            id = this?.id ?: LocalDatabase.DEFAULT_ID,
            careType = CareTypes.values()[careTypeOrdinal],
            date = toAppDate(this?.startDay, this?.startMonth, this?.startYear),
            progress = this?.progress
        )

    private fun toAppDate(day: Int?, month: Int?, year: Int?) =
        day?.let { "$day $month $year" }
}