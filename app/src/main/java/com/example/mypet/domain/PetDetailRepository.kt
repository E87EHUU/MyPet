package com.example.mypet.domain

import kotlinx.coroutines.flow.Flow
import com.example.mypet.domain.pet.detail.PetModel

interface PetDetailRepository {
    fun observePetDetail(): Flow<PetModel?>

    fun observePetListDetail(): Flow<List<PetModel?>>
}