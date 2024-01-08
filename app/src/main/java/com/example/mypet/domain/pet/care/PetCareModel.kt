package com.example.mypet.domain.pet.care

import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.domain.care.CareTypes

data class PetCareModel(
    val id: Int = DEFAULT_ID,
    val careType: CareTypes,
    val intervalStart: Long? = null,
    val nextStart: Long? = null,
)