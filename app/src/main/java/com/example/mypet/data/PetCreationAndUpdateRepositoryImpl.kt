package com.example.mypet.data

import android.net.Uri
import com.example.mypet.data.local.room.dao.PetCreationAndUpdateDao
import com.example.mypet.data.local.room.entity.LocalPetEntity
import com.example.mypet.data.local.room.model.pet.LocalPetModel
import com.example.mypet.domain.PetCreationAndUpdateRepository
import com.example.mypet.domain.pet.creation.PetCreationAndUpdateModel
import com.example.mypet.domain.pet.list.PetListModel
import javax.inject.Inject

class PetCreationAndUpdateRepositoryImpl @Inject constructor(
    private val petCreationAndUpdateDao: PetCreationAndUpdateDao
) : PetCreationAndUpdateRepository {


    override suspend fun addNewPetToDb(petCreationAndUpdateModel: PetCreationAndUpdateModel) {
        petCreationAndUpdateDao.addNewPetToDb(
            petCreationAndUpdateModel.toLocalPetMyEntity()
        )
    }

    override suspend fun getPetFromDbForUpdateDetails(petId: Int): PetListModel {
        return petCreationAndUpdateDao.getPetFromDbForUpdateDetails(petId).toPetListModel()
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
            dateOfBirthTimeMillis = dateOfBirth?.toLong(),
            weight = weight,
            sex = sex,
            isActive = isActive
        )

    private fun LocalPetModel.toPetListModel() =
        PetListModel(
            id = id,
            avatarUri = avatarPath?.let { Uri.parse(avatarPath) },
            name = name,
            age = age,
            weight = weight,
            kindOrdinal = kindOrdinal,
            breedOrdinal = breedOrdinal,
            sex = sex,
            isActive = isActive,
        )
}