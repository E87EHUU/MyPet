package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.LocalDatabase.Companion.FOOD_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AGE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.WEIGHT
import com.example.mypet.data.local.room.model.pet.LocalPetModel
import com.example.mypet.data.local.room.model.pet.LocalPetModel.Companion.BREED_NAME
import com.example.mypet.data.local.room.model.pet.LocalPetModel.Companion.FOOD_NAME
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalPetDetailDao {
    @Query(
        "SELECT " +
                "m.id $ID, " +
                "m.avatar $AVATAR, " +
                "m.name $NAME, " +
                "m.age $AGE, " +
                "m.weight $WEIGHT, " +
                "b.name $BREED_NAME, " +
                "f.id $FOOD_ID, " +
                "f.name $FOOD_NAME, " +
                "a.id ${ALARM_TABLE}_${ID}, " +
                "a.hour ${ALARM_TABLE}_${HOUR}, " +
                "a.minute ${ALARM_TABLE}_${MINUTE}, " +
                "a.is_active ${ALARM_TABLE}_${IS_ACTIVE} " +
                "FROM pet_my m " +
                "LEFT JOIN pet_breed b ON b.id = m.breed_id " +
                "LEFT JOIN pet_food f ON f.pet_my_id = m.id " +
                "LEFT JOIN alarm a ON a.id = f.alarm_id " +
                "WHERE m.is_active = 1 " +
                "ORDER BY m.id, f.id ASC"
    )
    fun observeActivePet(): Flow<List<LocalPetModel>>

    @Query("UPDATE alarm SET is_active = :alarmIsActive WHERE id = :alarmId")
    fun switchPetFoodAlarmState(alarmId: Int, alarmIsActive: Boolean): Int
}