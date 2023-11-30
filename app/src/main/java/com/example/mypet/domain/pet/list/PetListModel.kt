package com.example.mypet.domain.pet.list

import android.net.Uri

data class PetListModel(
    val id: Int,
    val avatarUri: Uri?,
    val name: String,
    val age: String?,
    val weight: String?,
    val kindOrdinal: Int,
    val breedOrdinal: Int?,
    val isActive: Boolean,
)
