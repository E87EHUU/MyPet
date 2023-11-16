package com.example.mypet.data

import android.net.Uri
import com.example.mypet.data.local.room.dao.LocalPetDao
import com.example.mypet.data.local.room.model.pet.LocalPetModel
import com.example.mypet.domain.PetRepository
import com.example.mypet.domain.pet.detail.PetModel
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject


class PetRepositoryImpl @Inject constructor(
    private val localPetDao: LocalPetDao,
) : PetRepository {
    override fun observePetList(activePetId: Int?) =
        localPetDao.observePetList(activePetId)
            .mapNotNull { localPetModels -> localPetModels.map { it.toPetModel() } }

    private fun LocalPetModel.toPetModel() =
        PetModel(
            id = id,
            avatarUri = avatarPath?.let { Uri.parse(avatarPath) },
            name = name,
            age = age,
            weight = weight,
            kindId = kindId,
            breedId = breedId,
            isActive = isActive,
        )
}