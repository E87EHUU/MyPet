package com.example.mypet.domain

import com.example.mypet.data.local.room.model.LocalPetBreedModel
import com.example.mypet.data.local.room.model.LocalPetKindModel
import kotlinx.coroutines.flow.Flow

interface PetCreationRepository {

    fun getKindList(): Flow<List<LocalPetKindModel>>

    fun getBreedList(kindId: Int): Flow<List<LocalPetBreedModel>>
}