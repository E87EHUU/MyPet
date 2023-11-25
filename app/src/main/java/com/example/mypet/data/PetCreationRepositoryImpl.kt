package com.example.mypet.data

import com.example.mypet.data.local.room.dao.PetCreationDao
import com.example.mypet.data.local.room.entity.LocalPetMyEntity
import com.example.mypet.domain.PetCreationRepository
import com.example.mypet.domain.pet.detail.PetModel
import javax.inject.Inject

class PetCreationRepositoryImpl @Inject constructor(
    private val petCreationDao: PetCreationDao
) : PetCreationRepository {


    override suspend fun addNewPetToDb(newPet: PetModel) {
        petCreationDao.addNewPetToDb(
            LocalPetMyEntity(
                id = generateUniqueId(),
                kindOrdinal = newPet.kindOrdinal,
                breedOrdinal = newPet.breedOrdinal,
                avatarPath = null,
                name = newPet.name,
                age = newPet.age,
                weight = newPet.weight,
                sex = 1,
                isActive = true
            )
        )
    }

    companion object {
        private var counter = 0

        fun generateUniqueId(): Int {
            return counter++
        }
    }
}