package com.example.mypet.domain.pet

import android.net.Uri

data class PetModel(
    val id: Int,
    val avatarUri: Uri?,
    val name: String,
    val age: Int?,
    val weight: Int?,
    val kindOrdinal: Int,
    val breedOrdinal: Int?,
    val isActive: Boolean,
)