package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.TITLE
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE
import com.example.mypet.data.local.room.entity.LocalPetFoodEntity.Companion.RATION
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.PET_FOOD_TABLE
import com.example.mypet.data.local.room.model.food.LocalFoodAlarmModel
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalFoodDao {
    @Query(
        "SELECT " +
                "f.id ${PET_FOOD_TABLE}_$ID, " +
                "f.title ${PET_FOOD_TABLE}_$TITLE, " +
                "f.ration ${PET_FOOD_TABLE}_$RATION, " +
                "a.id ${ALARM_TABLE}_$ID, " +
                "a.hour ${ALARM_TABLE}_$HOUR, " +
                "a.minute ${ALARM_TABLE}_$MINUTE, " +
                "a.is_active ${ALARM_TABLE}_$IS_ACTIVE " +
                "FROM pet_food f " +
                "LEFT JOIN alarm a ON a.id = f.alarm_id " +
                "WHERE f.pet_my_id = :petMyId"
    )
    fun observeFoodModels(petMyId: Int): Flow<List<LocalFoodAlarmModel>>
}