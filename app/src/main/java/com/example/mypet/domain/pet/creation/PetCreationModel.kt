package com.example.mypet.domain.pet.creation

import android.net.Uri

data class PetCreationModel(
    val id: Int,
    val avatarUri: Uri?,
    val name: String,
    val dateOfBirthTimeMillis: Long?,
    val weight: Int?,
    val sex: Int,
    val kindOrdinal: Int,
    val breedOrdinal: Int?,
    val isActive: Boolean,
)
