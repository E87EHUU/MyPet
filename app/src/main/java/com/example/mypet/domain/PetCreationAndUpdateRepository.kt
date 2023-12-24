package com.example.mypet.domain

import com.example.mypet.domain.pet.creation.PetCreationAndUpdateModel
import com.example.mypet.domain.pet.list.PetListMainModel

interface PetCreationAndUpdateRepository {

    suspend fun addNewPetToDb(petCreationAndUpdateModel: PetCreationAndUpdateModel)

    suspend fun getPetFromDbForUpdateDetails(petId: Int): PetListMainModel?

    suspend fun updatePetDetailsInDb(updatedPet: PetCreationAndUpdateModel)
}