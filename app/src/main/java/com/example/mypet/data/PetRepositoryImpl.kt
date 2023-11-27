package com.example.mypet.data

import android.net.Uri
import com.example.mypet.data.local.room.dao.LocalPetDao
import com.example.mypet.data.local.room.model.pet.LocalPetModel
import com.example.mypet.domain.PetRepository
import com.example.mypet.domain.pet.PetModel
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject


class PetRepositoryImpl @Inject constructor(
    private val localPetDao: LocalPetDao,
) : PetRepository {
    override fun getPets() =
        localPetDao.getLocalPetModels()
            .mapNotNull { localPetModels -> localPetModels.map { it.toPetModel() } }

    override suspend fun deletePet(petId: Int) {
        localPetDao.deletePet(petId)
    }

    private fun LocalPetModel.toPetModel() =
        PetModel(
            id = petId,
            avatarUri = petAvatarPath?.let { Uri.parse(petAvatarPath) },
            name = petName,
            age = petAge,
            weight = petWeight,
            kindOrdinal = petKindOrdinal,
            breedOrdinal = petBreedOrdinal,
            isActive = petIsActive,
        )
}