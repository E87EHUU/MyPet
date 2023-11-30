package com.example.mypet.data

import com.example.mypet.data.local.room.dao.LocalPetDao
import com.example.mypet.domain.PetRepository
import com.example.mypet.domain.care.CareTypes
import com.example.mypet.domain.pet.care.PetCareModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject


class PetRepositoryImpl @Inject constructor(
    private val localPetDao: LocalPetDao,
) : PetRepository {
    override fun getPet() =
        localPetDao.getLocalPetModels()
            .mapNotNull { its -> its.map { it.toPetListModel() } }

    override fun getFood(petId: Int) =
        localPetDao.getLocalAlarmMinModels(petId, CareTypes.FOOD.ordinal)
            .mapNotNull { its -> its.map { it.toPetFoodAlarmModel() } }

    override fun getCare(petId: Int) =
        flow {
            emit(emptyList<PetCareModel>())
        }

    override suspend fun deletePet(petId: Int) {
        localPetDao.deletePet(petId)
    }
}