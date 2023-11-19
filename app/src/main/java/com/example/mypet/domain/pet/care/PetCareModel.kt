package com.example.mypet.domain.pet.care

data class PetCareModel(
    val id: Int,
    val iconResId: Int,
    val title: String,
    val date: String,
    val progress: Int,
)