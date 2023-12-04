package com.example.mypet.data.local.room.model.pet

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AGE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.SEX
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.WEIGHT

data class LocalPetModel(
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = AVATAR_PATH)
    val avatarPath: String?,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = AGE)
    val age: String?,
    @ColumnInfo(name = WEIGHT)
    val weight: String?,
    @ColumnInfo(name = KIND_ORDINAL)
    val kindOrdinal: Int,
    @ColumnInfo(name = BREED_ORDINAL)
    val breedOrdinal: Int?,
    @ColumnInfo(name = SEX)
    val sex: Int?,
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean,
)