package com.example.mypet.data.local.room.model.pet

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.HOUR
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.MINUTE
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.PROGRESS
import com.example.mypet.data.local.room.entity.LocalStartEntity.Companion.TIME_IN_MILLIS

data class LocalPetCareModel(
    @ColumnInfo(name = ID)
    val id: Int?,
    @ColumnInfo(name = TIME_IN_MILLIS)
    val timeInMillis: Int? = null,
    @ColumnInfo(name = HOUR)
    val hour: Int? = null,
    @ColumnInfo(name = MINUTE)
    val minute: Int? = null,
    @ColumnInfo(name = PROGRESS)
    val progress: Int? = null,
)