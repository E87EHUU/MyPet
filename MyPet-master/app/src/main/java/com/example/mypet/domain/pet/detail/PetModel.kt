package com.example.mypet.domain.pet.detail

import android.net.Uri

data class PetModel(
    val id: Int,
    val avatar: Uri,
    val name: String?,
    val age: String?,
    val weight: String?,
    val breedName: String,
)