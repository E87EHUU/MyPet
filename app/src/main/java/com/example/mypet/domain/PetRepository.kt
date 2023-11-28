package com.example.mypet.domain

import com.example.mypet.domain.pet.PetFoodAlarmModel
import com.example.mypet.domain.pet.PetModel
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    fun getPetModels(): Flow<List<PetModel>>
    fun getPetFoodAlarmModels(petId: Int): Flow<List<PetFoodAlarmModel>>
    suspend fun deletePet(petId: Int)
}