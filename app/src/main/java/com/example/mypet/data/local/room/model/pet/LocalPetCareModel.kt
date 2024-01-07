package com.example.mypet.data.local.room.model.pet

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.BEFORE_START
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.NEXT_START
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.CARE_TYPE_ORDINAL

data class LocalPetCareModel(
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = CARE_TYPE_ORDINAL)
    val careTypeOrdinal: Int,
    @ColumnInfo(name = BEFORE_START)
    val beforeStart: Long? = null,
    @ColumnInfo(name = NEXT_START)
    val nextStart: Long? = null,
)