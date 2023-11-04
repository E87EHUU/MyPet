package com.example.mypet.domain

import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.domain.pet.detail.SwitchPetFoodAlarmStateModel
import kotlinx.coroutines.flow.Flow

interface PetDetailRepository {
    fun observePetDetail(): Flow<PetModel?>
    suspend fun switchPetFoodAlarmState(switchPetFoodAlarmStateModel: SwitchPetFoodAlarmStateModel)
}