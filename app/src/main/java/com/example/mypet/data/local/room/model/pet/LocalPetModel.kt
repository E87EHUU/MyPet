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
    val petId: Int,
    @ColumnInfo(name = AVATAR_PATH)
    val petAvatarPath: String?,
    @ColumnInfo(name = NAME)
    val petName: String,
    @ColumnInfo(name = AGE)
    val petAge: Int?,
    @ColumnInfo(name = WEIGHT)
    val petWeight: Int?,
    @ColumnInfo(name = KIND_ORDINAL)
    val petKindOrdinal: Int,
    @ColumnInfo(name = BREED_ORDINAL)
    val petBreedOrdinal: Int?,
    @ColumnInfo(name = IS_ACTIVE)
    val petIsActive: Boolean,
) {
    fun toPetModel() =
        PetModel(
            id = petId,
            avatarUri = petAvatarPath?.let { Uri.parse(petAvatarPath) },
            name = petName,
            age = petAge,
            weight = petWeight,
            kindOrdinal = petKindOrdinal,
            breedOrdinal = petBreedOrdinal,
            isActive = petIsActive,
        )
}