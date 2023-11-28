package com.example.mypet.data.local.room.model.pet

import android.net.Uri
import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AGE
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.WEIGHT
import com.example.mypet.domain.pet.PetModel

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
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean,
) {
    fun toPetModel() =
        PetModel(
            id = id,
            avatarUri = avatarPath?.let { Uri.parse(avatarPath) },
            name = name,
            age = age,
            weight = weight,
            kindOrdinal = kindOrdinal,
            breedOrdinal = breedOrdinal,
            isActive = isActive,
        )
}