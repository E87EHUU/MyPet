package com.example.mypet.domain

import com.example.mypet.domain.pet.creation.PetCreationAndUpdateModel
import com.example.mypet.domain.pet.detail.PetModel

interface PetCreationAndUpdateRepository {

    suspend fun addNewPetToDb(petCreationAndUpdateModel: PetCreationAndUpdateModel)

    suspend fun getPetFromDbForUpdateDetails(petId: Int): PetModel

    suspend fun updatePetDetailsInDb(updatedPet: PetCreationAndUpdateModel)
}