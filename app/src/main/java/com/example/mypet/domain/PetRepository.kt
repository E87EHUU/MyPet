package com.example.mypet.domain

import com.example.mypet.domain.pet.PetModel
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    fun getPets(): Flow<List<PetModel>>
    suspend fun deletePet(petId: Int)
}