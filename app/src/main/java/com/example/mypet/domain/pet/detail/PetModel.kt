package com.example.mypet.domain.pet.detail

import android.net.Uri

data class PetModel(
    val id: Int,
    val avatarUri: Uri,
    val name: String?,
    val age: String?,
    val weight: String?,
    val kindName: String,
    val breedName: String?,
    val isActive: Boolean,
)