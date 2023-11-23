package com.example.mypet.domain

import com.example.mypet.domain.pet.detail.PetModel
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    fun observePetList(): Flow<List<PetModel>>

    suspend fun observeDeletePet(petId: Int)
}