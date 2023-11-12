package com.example.mypet.data.local.room.model.pet

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AGE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR_URI
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.WEIGHT
import com.example.mypet.data.local.room.entity.PET_FOOD_TABLE

data class LocalPetModel(
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = AVATAR_URI)
    val avatar: String,
    @ColumnInfo(name = NAME)
    val name: String?,
    @ColumnInfo(name = AGE)
    val age: String?,
    @ColumnInfo(name = WEIGHT)
    val weight: String?,
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean,

    @ColumnInfo(name = BREED_NAME)
    val breedName: String,

    @ColumnInfo(name = "${PET_FOOD_TABLE}_${ID}")
    val foodId: Int,
    @ColumnInfo(name = FOOD_NAME)
    val foodName: String,

    @ColumnInfo(name = "${ALARM_TABLE}_${ID}")
    val alarmId: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_${HOUR}")
    val alarmHour: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_${MINUTE}")
    val alarmMinute: Int?,
    @ColumnInfo(name = "${ALARM_TABLE}_${IS_ACTIVE}")
    val alarmIsActive: Boolean?
) {
    companion object {
        const val BREED_NAME = "breed_name"
        const val FOOD_NAME = "food_name"
    }
}