package com.example.mypet.domain.pet.creation

data class PetCreationAndUpdateModel(
    val id: Int,
    val avatarUri: String?,
    val name: String,
    val dateOfBirth: Long?,
    val weight: Float?,
    val sex: Int?,
    val kindOrdinal: Int,
    val breedOrdinal: Int?,
    val isActive: Boolean,
)
