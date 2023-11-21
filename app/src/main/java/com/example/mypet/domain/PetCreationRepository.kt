package com.example.mypet.domain

import com.example.mypet.domain.pet.detail.PetModel

interface PetCreationRepository {

    suspend fun addNewPetToDb(newPet: PetModel)
}