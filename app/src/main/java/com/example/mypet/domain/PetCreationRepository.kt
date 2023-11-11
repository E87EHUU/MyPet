package com.example.mypet.domain

import kotlinx.coroutines.flow.Flow

interface PetCreationRepository {

    fun getKindList(): Flow<List<String>>
}