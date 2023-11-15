package com.example.mypet.data.local.room.model.pet

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AGE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.WEIGHT
import com.example.mypet.data.local.room.entity.PET_BREED_TABLE
import com.example.mypet.data.local.room.entity.PET_KIND_TABLE

data class LocalPetModel(
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = AVATAR_PATH)
    val avatarPath: String?,
    @ColumnInfo(name = NAME)
    val name: String?,
    @ColumnInfo(name = AGE)
    val age: String?,
    @ColumnInfo(name = WEIGHT)
    val weight: String?,
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean,

    @ColumnInfo(name = "${PET_KIND_TABLE}_$ID")
    val kindId: Int,
    @ColumnInfo(name = "${PET_BREED_TABLE}_$ID")
    val breedId: Int?,
)