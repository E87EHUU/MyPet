package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.KIND_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME

const val PET_BREED_TABLE = "pet_breed"

@Entity(tableName = PET_BREED_TABLE)
data class LocalPetBreedEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,
    @ColumnInfo(name = KIND_ID)
    val kindId: Int,

    @ColumnInfo(name = NAME)
    val name: String,
)