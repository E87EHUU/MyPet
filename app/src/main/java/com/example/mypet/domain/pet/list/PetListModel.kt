package com.example.mypet.domain.pet.list

data class PetListModel(
    val id: Int,
    val avatarUri: String?,
    val name: String,
    val dateOfBirth: Long?,
    val weight: Int?,
    val kindOrdinal: Int,
    val breedOrdinal: Int?,
    val sex: Int?,
    val isActive: Boolean,
)
