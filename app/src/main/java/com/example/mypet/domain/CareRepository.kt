package com.example.mypet.domain

import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.CareEndModel
import com.example.mypet.domain.care.CareMainModel
import com.example.mypet.domain.care.CareModel
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.CareStartModel
import kotlinx.coroutines.flow.Flow

interface CareRepository {
    suspend fun getCareMainModel(petId: Int, careTypeOrdinal: Int): Flow<CareMainModel>
    suspend fun getCareStartModel(careId: Int, careTypeOrdinal: Int): Flow<CareStartModel?>
    suspend fun getCareRepeatModel(careId: Int, careTypeOrdinal: Int): Flow<CareRepeatModel?>
    suspend fun getCareEndModel(careId: Int, careTypeOrdinal: Int): Flow<CareEndModel?>
    suspend fun getCareAlarmModel(careId: Int, careTypeOrdinal: Int): Flow<CareAlarmModel>
    suspend fun saveCareModels(careModels: List<CareModel>): Flow<Unit>
}