package com.example.mypet.data.local.room.model.pet

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.DATE_OF_BIRTH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.SEX
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.WEIGHT
import com.example.mypet.domain.pet.list.PetListModel

data class LocalPetModel(
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = AVATAR_PATH)
    val avatarPath: String?,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = DATE_OF_BIRTH)
    val dateOfBirth: Long?,
    @ColumnInfo(name = WEIGHT)
    val weight: Int?,
    @ColumnInfo(name = KIND_ORDINAL)
    val kindOrdinal: Int,
    @ColumnInfo(name = BREED_ORDINAL)
    val breedOrdinal: Int?,
    @ColumnInfo(name = SEX)
    val sex: Int?,
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean,
) {
    fun toPetListModel() =
        PetListModel(
            id = id,
            avatarUri = avatarPath,
            name = name,
            dateOfBirth = dateOfBirth,
            weight = weight,
            kindOrdinal = kindOrdinal,
            breedOrdinal = breedOrdinal,
            sex = sex,
            isActive = isActive,
        )
}