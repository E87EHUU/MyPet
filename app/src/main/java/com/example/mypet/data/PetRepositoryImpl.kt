package com.example.mypet.data

import com.example.mypet.data.local.room.dao.LocalPetDao
import com.example.mypet.domain.PetRepository
import com.example.mypet.domain.care.CareTypes
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject


class PetRepositoryImpl @Inject constructor(
    private val localPetDao: LocalPetDao,
) : PetRepository {
    override fun getPetModels() =
        localPetDao.getLocalPetModels()
            .mapNotNull { its -> its.map { it.toPetModel() } }

    override fun getPetFoodAlarmModels(petId: Int) =
        localPetDao.getLocalPetFoodModel(petId, CareTypes.FOOD.ordinal)
            .mapNotNull { its -> its.map { it.toPetFoodAlarmModel() } }

    override suspend fun deletePet(petId: Int) {
        localPetDao.deletePet(petId)
    }
}