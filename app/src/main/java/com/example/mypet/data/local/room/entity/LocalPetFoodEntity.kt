package com.example.mypet.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.LocalDatabase.Companion.PET_MY_ID

const val TABLE_NAME = "pet_food"

@Entity(tableName = TABLE_NAME)
data class LocalPetFoodEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,

    @ColumnInfo(name = PET_MY_ID)
    val petMyId: Int,

    @ColumnInfo(name = "${ALARM_TABLE}_${ID}")
    val alarmId: Int?,

    @ColumnInfo(name = NAME)
    val name: String,
)