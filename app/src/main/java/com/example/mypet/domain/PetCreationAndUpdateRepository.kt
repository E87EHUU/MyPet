package com.example.mypet.domain

import com.example.mypet.domain.pet.creation.PetCreationAndUpdateModel
import com.example.mypet.domain.pet.list.PetListModel

interface PetCreationAndUpdateRepository {

    suspend fun addNewPetToDb(petCreationAndUpdateModel: PetCreationAndUpdateModel)

    suspend fun getPetFromDbForUpdateDetails(petId: Int): PetListModel?

    suspend fun updatePetDetailsInDb(updatedPet: PetCreationAndUpdateModel)
}