package com.example.mypet.data

import android.net.Uri
import com.example.mypet.data.local.room.dao.PetCreationDao
import com.example.mypet.data.local.room.entity.LocalPetMyEntity
import com.example.mypet.data.local.room.model.pet.LocalPetModel
import com.example.mypet.domain.PetCreationRepository
import com.example.mypet.domain.pet.creation.PetCreationModel
import com.example.mypet.domain.pet.detail.PetModel
import javax.inject.Inject

class PetCreationRepositoryImpl @Inject constructor(
    private val petCreationDao: PetCreationDao
) : PetCreationRepository {


    override suspend fun addNewPetToDb(petCreationModel: PetCreationModel) {
        petCreationDao.addNewPetToDb(
            petCreationModel.toLocalPetMyEntity()
        )
    }

    override suspend fun getPetFromDbForUpdateDetails(petId: Int): PetModel {
        return petCreationDao.getPetFromDbForUpdateDetails(petId).toPetModel()
    }

    override suspend fun updatePetDetailsInDb(updatedPet: PetCreationModel) {
        petCreationDao.updatePetDetailsInDb(
            updatedPet.toLocalPetMyEntity()
        )
    }

    private fun PetCreationModel.toLocalPetMyEntity() =
        LocalPetMyEntity(
            id = id,
            kindOrdinal = kindOrdinal,
            breedOrdinal = breedOrdinal,
            avatarPath = avatarUri,
            name = name,
            dateOfBirthTimeMillis = dateOfBirth?.toLong(),
            weight = weight,
            sex = sex,
            isActive = isActive
        )

    private fun LocalPetModel.toPetModel() =
        PetModel(
            id = id,
            avatarUri = avatarPath?.let { Uri.parse(avatarPath) },
            name = name,
            age = age,
            weight = weight,
            kindOrdinal = kindOrdinal,
            breedOrdinal = breedOrdinal,
            isActive = isActive,
        )
}