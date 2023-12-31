package com.example.mypet.domain

import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.food.PetFoodModel
import com.example.mypet.domain.pet.list.PetListMainModel
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    fun getPetListMainModels(): Flow<List<PetListMainModel>>
    fun getPetFoodModel(petId: Int): Flow<PetFoodModel>
    fun getCareModels(petId: Int): Flow<List<PetCareModel>>

    suspend fun deletePet(petId: Int)
}