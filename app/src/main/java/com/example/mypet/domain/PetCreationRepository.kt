package com.example.mypet.domain

import com.example.mypet.domain.pet.creation.PetCreationModel

interface PetCreationRepository {

    suspend fun addNewPetToDb(petCreationModel: PetCreationModel)
}