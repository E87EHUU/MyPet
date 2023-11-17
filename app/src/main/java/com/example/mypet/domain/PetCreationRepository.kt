package com.example.mypet.domain

import com.example.mypet.data.local.room.entity.LocalPetMyEntity
import com.example.mypet.data.local.room.model.LocalPetBreedModel
import com.example.mypet.data.local.room.model.LocalPetKindModel
import com.example.mypet.data.local.room.model.LocalPetModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface PetCreationRepository {

    fun getKindList(): Flow<List<LocalPetKindModel>>

    fun getBreedList(kindId: Int): Flow<List<LocalPetBreedModel>>

    suspend fun addNewPetToDb(name: String, kindId: Int, breedId: Int, dateOfBirth: String, weight: Int)
}