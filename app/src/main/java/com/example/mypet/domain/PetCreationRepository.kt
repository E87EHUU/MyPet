package com.example.mypet.domain

import com.example.mypet.domain.pet.PetModel

interface PetCreationRepository {

    suspend fun addNewPetToDb(newPet: PetModel)
}