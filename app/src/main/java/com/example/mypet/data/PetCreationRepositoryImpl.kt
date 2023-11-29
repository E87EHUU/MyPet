package com.example.mypet.data

import com.example.mypet.data.local.room.dao.PetCreationDao
import com.example.mypet.data.local.room.entity.LocalPetMyEntity
import com.example.mypet.domain.PetCreationRepository
import com.example.mypet.domain.pet.creation.PetCreationModel
import javax.inject.Inject

class PetCreationRepositoryImpl @Inject constructor(
    private val petCreationDao: PetCreationDao
) : PetCreationRepository {


    override suspend fun addNewPetToDb(petCreationModel: PetCreationModel) {
        petCreationDao.addNewPetToDb(
            petCreationModel.toLocalPetMyEntity()
        )
    }

    private fun PetCreationModel.toLocalPetMyEntity() =
        LocalPetMyEntity(
            id = id,
            kindOrdinal = kindOrdinal,
            breedOrdinal = breedOrdinal,
            avatarPath = avatarUri.toString(),
            name = name,
            dateOfBirthTimeMillis = dateOfBirthTimeMillis,
            weight = weight,
            sex = sex,
            isActive = isActive
        )
}