package com.example.mypet.domain

import com.example.mypet.domain.pet.PetListModel
import com.example.mypet.domain.pet.care.PetCareModel
import com.example.mypet.domain.pet.food.PetFoodModel
import kotlinx.coroutines.flow.Flow

interface PetRepository {
    fun getPet(): Flow<List<PetListModel>>
    fun getPetFoodModel(petId: Int): Flow<PetFoodModel>
    fun getCare(petId: Int): Flow<List<PetCareModel>>

    suspend fun deletePet(petId: Int)
}