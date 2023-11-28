package com.example.mypet.domain

import com.example.mypet.domain.pet.creation.PetCreationModel
import com.example.mypet.domain.pet.detail.PetModel

interface PetCreationRepository {

    suspend fun addNewPetToDb(petCreationModel: PetCreationModel)

    suspend fun getPetFromDbForUpdateDetails(petId: Int): PetModel

    suspend fun updatePetDetailsInDb(updatedPet: PetCreationModel)
}