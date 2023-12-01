package com.example.mypet.data.local.room.model.pet

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.PROGRESS
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.START_DAY
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.START_MONTH
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.START_YEAR

data class LocalPetCareModel(
    @ColumnInfo(name = ID)
    val id: Int?,
    @ColumnInfo(name = START_DAY)
    val startDay: Int? = null,
    @ColumnInfo(name = START_MONTH)
    val startMonth: Int? = null,
    @ColumnInfo(name = START_YEAR)
    val startYear: Int? = null,
    @ColumnInfo(name = PROGRESS)
    val progress: Int? = null,
)