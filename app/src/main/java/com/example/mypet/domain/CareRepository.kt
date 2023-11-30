package com.example.mypet.domain

import com.example.mypet.domain.care.CareViewHolderModel
import kotlinx.coroutines.flow.Flow

interface CareRepository {
    fun getCareViewHolderModels(careId: Int, careTypeOrdinal: Int): Flow<List<CareViewHolderModel>>
    fun getCareViewHolderAlarmModel(careId: Int): Flow<List<CareViewHolderModel>>
}