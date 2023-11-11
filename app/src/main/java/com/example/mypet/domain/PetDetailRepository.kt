package com.example.mypet.domain

import kotlinx.coroutines.flow.Flow
import com.example.mypet.domain.pet.detail.PetModel

interface PetDetailRepository {
    fun observePetDetail(): Flow<PetModel?>
    suspend fun switchPetFoodAlarmState(switchPetFoodAlarmStateModel: SwitchPetFoodAlarmStateModel)

    fun observePetListDetail(): Flow<List<PetModel?>>
}