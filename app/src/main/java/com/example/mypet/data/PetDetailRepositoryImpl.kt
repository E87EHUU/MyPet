package com.example.mypet.data

import android.net.Uri
import com.example.mypet.data.local.room.dao.LocalPetDetailDao
import com.example.mypet.data.local.room.model.LocalPetModel
import com.example.mypet.domain.PetDetailRepository
import com.example.mypet.domain.pet.detail.PetModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PetDetailRepositoryImpl @Inject constructor(
    private val localPetDetailDao: LocalPetDetailDao
) : PetDetailRepository {
    override fun observePetDetail() =
        localPetDetailDao.observeActivePet()
            .map { it?.toPetModel() }

    override fun observePetListDetail(): Flow<List<PetModel?>> {
        return localPetDetailDao.observePetList().map { petList ->
            petList.map {
                it?.toPetModel()
            }
        }
    }

    private fun LocalPetModel.toPetModel() =
        PetModel(
            id = id,
            avatar = Uri.parse(avatar),
            name = name,
            age = age,
            weight = weight,
            breedName = breedName
        )
}