package com.example.mypet.domain

import com.example.mypet.domain.care.CareViewHolderModel
import kotlinx.coroutines.flow.Flow

interface CareRepository {
    fun getCareModels(careId: Int, careTypeOrdinal: Int): Flow<List<CareViewHolderModel>>
}