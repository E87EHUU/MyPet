package com.example.mypet.domain

import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.food.PetFoodAlarmModel
import com.example.mypet.domain.pet.PetModel
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    fun getPet(): Flow<List<PetModel>>
    fun getFood(petId: Int): Flow<List<PetFoodAlarmModel>>
    fun getCare(petId: Int): Flow<List<PetCareModel>>

    suspend fun deletePet(petId: Int)
}