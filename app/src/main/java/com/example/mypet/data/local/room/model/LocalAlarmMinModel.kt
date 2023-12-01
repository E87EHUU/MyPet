package com.example.mypet.data.local.room.model

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE
import com.example.mypet.domain.care.CareViewHolderAlarmModel
import com.example.mypet.domain.pet.food.PetFoodAlarmModel
import com.example.mypet.ui.toAppTime

data class LocalAlarmMinModel(
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = HOUR)
    val hour: Int,
    @ColumnInfo(name = MINUTE)
    val minute: Int,
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean
) {
    fun toPetFoodAlarmModel() =
        PetFoodAlarmModel(
            id = id,
            time = toAppTime(hour, minute),
            isActive = isActive
        )

    fun toCareViewHolderAlarmModel() =
        CareViewHolderAlarmModel(
            id = id,
            time = toAppTime(hour, minute),
            isActive = isActive
        )
}