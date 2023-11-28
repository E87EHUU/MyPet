package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID

const val PET_FOOD_TABLE = "pet_food"

@Entity(tableName = PET_FOOD_TABLE)
data class LocalPetFoodEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,

    @ColumnInfo(name = "${PET_MY_TABLE}_$ID")
    val petId: Int,

    @ColumnInfo(name = "${ALARM_TABLE}_$ID")
    val alarmId: Int,
)