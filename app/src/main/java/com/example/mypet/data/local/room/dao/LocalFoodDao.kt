package com.example.mypet.data.local.room.dao

import androidx.room.Dao


@Dao
interface LocalFoodDao {
/*    @Query(
        "SELECT " +
                "a.id ${ALARM_TABLE}_$ID, " +
                "a.description ${ALARM_TABLE}_$DESCRIPTION, " +
                "a.hour ${ALARM_TABLE}_$HOUR, " +
                "a.minute ${ALARM_TABLE}_$MINUTE, " +
                "a.is_active ${ALARM_TABLE}_$IS_ACTIVE " +
                "FROM pet_food f " +
                "LEFT JOIN alarm a ON a.id = f.alarm_id " +
                "WHERE f.pet_my_id = :petMyId"
    )
    fun observeFoodModels(petMyId: Int): Flow<List<LocalFoodAlarmModel>>*/
}