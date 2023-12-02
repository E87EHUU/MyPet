package com.example.mypet.domain

import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.CareMainModel
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.CareStartModel
import kotlinx.coroutines.flow.Flow

interface CareRepository {
    fun getCareMainModel(careId: Int, careTypeOrdinal: Int): Flow<CareMainModel>
    fun getCareStartModel(careId: Int, careTypeOrdinal: Int): Flow<CareStartModel?>
    fun getCareRepeatModel(careId: Int, careTypeOrdinal: Int): Flow<CareRepeatModel?>
    fun getCareAlarmModel(careId: Int, careTypeOrdinal: Int): Flow<CareAlarmModel?>
}