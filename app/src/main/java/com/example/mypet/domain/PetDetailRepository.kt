package com.example.mypet.domain

import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.domain.pet.detail.SwitchPetFoodAlarmStateModel
import kotlinx.coroutines.flow.Flow

interface PetDetailRepository {
    fun observePetListDetail(): Flow<List<PetModel>>
    suspend fun switchPetFoodAlarmState(switchPetFoodAlarmStateModel: SwitchPetFoodAlarmStateModel)
}