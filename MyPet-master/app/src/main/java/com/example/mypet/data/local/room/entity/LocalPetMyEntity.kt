package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.BREED_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class LocalPetMyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,
    @ColumnInfo(name = BREED_ID)
    val breedId: Int,

    @ColumnInfo(name = AVATAR)
    val avatar: String,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = AGE)
    val age: Int?,
    @ColumnInfo(name = WEIGHT)
    val weight: Int?,
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean,
) {
    companion object {
        const val TABLE_NAME = "pet_my"

        const val AVATAR = "avatar"
        const val NAME = "name"
        const val AGE = "age"
        const val WEIGHT = "weight"
        const val IS_ACTIVE = "is_active"
    }
}