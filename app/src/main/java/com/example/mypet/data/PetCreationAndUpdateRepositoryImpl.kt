package com.example.mypet.data

import com.example.mypet.data.local.room.dao.PetCreationAndUpdateDao
import com.example.mypet.data.local.room.entity.LocalPetEntity
import com.example.mypet.domain.PetCreationAndUpdateRepository
import com.example.mypet.domain.pet.creation.PetCreationAndUpdateModel
import com.example.mypet.domain.pet.list.PetListMainModel
import javax.inject.Inject

class PetCreationAndUpdateRepositoryImpl @Inject constructor(
    private val petCreationAndUpdateDao: PetCreationAndUpdateDao
) : PetCreationAndUpdateRepository {
    override suspend fun addNewPetToDb(petCreationAndUpdateModel: PetCreationAndUpdateModel) {
        petCreationAndUpdateDao.addNewPetToDb(
            petCreationAndUpdateModel.toLocalPetMyEntity()
        )
    }

    override suspend fun getPetFromDbForUpdateDetails(petId: Int): PetListMainModel {
        return petCreationAndUpdateDao.getPetFromDbForUpdateDetails(petId).toPetListMainModel()
    }

    override suspend fun updatePetDetailsInDb(updatedPet: PetCreationAndUpdateModel) {
        petCreationAndUpdateDao.updatePetDetailsInDb(
            updatedPet.toLocalPetMyEntity()
        )
    }

    private fun PetCreationAndUpdateModel.toLocalPetMyEntity() =
        LocalPetEntity(
            id = id,
            kindOrdinal = kindOrdinal,
            breedOrdinal = breedOrdinal,
            avatarPath = avatarUri,
            name = name,
            dateOfBirth = dateOfBirth?.toLong(),
            weight = weight,
            sex = sex,
            isActive = isActive
        )
}