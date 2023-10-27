package com.example.mypet.data.local.room.model

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AGE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.NAME
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.WEIGHT

data class LocalPetModel(
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = AVATAR)
    val avatar: String,
    @ColumnInfo(name = NAME)
    val name: String?,
    @ColumnInfo(name = AGE)
    val age: String?,
    @ColumnInfo(name = WEIGHT)
    val weight: String?,
    @ColumnInfo(name = BREED_NAME)
    val breedName: String,
) {
    companion object {
        const val BREED_NAME = "breed_name"
    }
}