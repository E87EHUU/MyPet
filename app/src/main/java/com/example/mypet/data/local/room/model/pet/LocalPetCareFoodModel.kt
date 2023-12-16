package com.example.mypet.data.local.room.model.pet

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID

data class LocalPetCareFoodModel(
    @ColumnInfo(name = ID)
    val id: Int,
)