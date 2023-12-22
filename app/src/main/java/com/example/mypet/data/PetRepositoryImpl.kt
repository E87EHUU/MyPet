package com.example.mypet.data

import androidx.room.Transaction
import com.example.mypet.data.local.room.LocalDatabase
import com.example.mypet.data.local.room.dao.LocalPetDao
import com.example.mypet.data.local.room.model.pet.LocalPetCareFoodModel
import com.example.mypet.data.local.room.model.pet.LocalPetCareModel
import com.example.mypet.domain.PetRepository
import com.example.mypet.domain.care.CareTypes
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.food.PetFoodModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import java.util.Calendar
import javax.inject.Inject


class PetRepositoryImpl @Inject constructor(
    private val localPetDao: LocalPetDao,
) : PetRepository {
    override fun getPetListMainModels() =
        localPetDao.getLocalPetModels()
            .mapNotNull { its -> its.map { it.toPetListMainModel() } }

    @Transaction
    override fun getPetFoodModel(petId: Int) =
        combine(
            localPetDao.getLocalPetCareFoodModel(petId, CareTypes.FOOD.ordinal),
            localPetDao.getLocalAlarmMinModels(petId, CareTypes.FOOD.ordinal)
        ) { localPetCareModel, localAlarmMinModels ->
                PetFoodModel(
                    care = localPetCareModel.toPetCareDetailModel(),
                    alarmModels = localAlarmMinModels.map { it.toAlarmMinModel() }
                )
        }

    override fun getCareModels(petId: Int) =
        localPetDao.getLocalPetCareModels(petId, Calendar.getInstance().timeInMillis)
            .map { its -> its.map { it.toPetCareDetailModel() } }

    override suspend fun deletePet(petId: Int) {
        localPetDao.deletePet(petId)
   }

    private fun LocalPetCareModel.toPetCareDetailModel() =
        PetCareModel(
            id = id,
            careType = CareTypes.entries[careTypeOrdinal],
            progress = progress,
            nextStart = nextStart
        )

    private fun LocalPetCareFoodModel?.toPetCareDetailModel() =
        PetCareModel(
            id = this?.id ?: LocalDatabase.DEFAULT_ID,
            careType = CareTypes.FOOD,
        )
}