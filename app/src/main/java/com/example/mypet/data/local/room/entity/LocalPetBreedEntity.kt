package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.entity.LocalPetBreedEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class LocalPetBreedEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,

    @ColumnInfo(name = NAME)
    val name: String,
) {
    companion object {
        const val TABLE_NAME = "pet_breed"

        const val NAME = "name"
    }
}