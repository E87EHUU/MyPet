package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AGE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.WEIGHT
import com.example.mypet.data.local.room.model.LocalPetModel
import com.example.mypet.data.local.room.model.LocalPetModel.Companion.BREED_NAME
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalPetDetailDao {
    @Query(
        "SELECT " +
                "m.id $ID, " +
                "m.avatar_uri $AVATAR_URI, " +
                "m.name $NAME, " +
                "m.age $AGE, " +
                "m.weight $WEIGHT, " +
                "b.name $BREED_NAME, " +
                "f.id ${PET_FOOD_TABLE}_$ID, " +
                "f.title $FOOD_NAME, " +
                "a.id ${ALARM_TABLE}_$ID, " +
                "a.hour ${ALARM_TABLE}_$HOUR, " +
                "a.minute ${ALARM_TABLE}_$MINUTE, " +
                "a.is_active ${ALARM_TABLE}_$IS_ACTIVE " +
                "FROM pet_my m " +
                "LEFT JOIN pet_breed b ON b.id = m.pet_breed_id " +
                "LEFT JOIN pet_food f ON f.pet_my_id = m.id " +
                "LEFT JOIN alarm a ON a.id = f.alarm_id " +
                "WHERE m.is_active = 1 " +
                "ORDER BY m.id, f.id ASC"
    )
    fun observeActivePet(): Flow<List<LocalPetModel>>

    @Query(
        "SELECT " +
                "f.id $ID, " +
                "f.title $TITLE, " +
                "b.icon_res_id $ICON_RES_ID, " +
                "a.id ${ALARM_TABLE}_${ID}, " +
                "a.hour $HOUR, " +
                "a.minute $MINUTE, " +
                "a.repeat_monday $IS_REPEAT_MONDAY, " +
                "a.repeat_tuesday $IS_REPEAT_TUESDAY, " +
                "a.repeat_wednesday $IS_REPEAT_WEDNESDAY, " +
                "a.repeat_thursday $IS_REPEAT_THURSDAY, " +
                "a.repeat_friday $IS_REPEAT_FRIDAY, " +
                "a.repeat_saturday $IS_REPEAT_SATURDAY, " +
                "a.repeat_sunday $IS_REPEAT_SUNDAY, " +
                "a.ringtone_path $RINGTONE_PATH, " +
                "a.is_vibration $IS_VIBRATION, " +
                "a.is_delay $IS_DELAY, " +
                "a.is_active $IS_ACTIVE " +
                "FROM alarm a " +
                "LEFT JOIN pet_food f ON f.alarm_id = a.id " +
                "LEFT JOIN pet_my m ON m.id = f.pet_my_id " +
                "LEFT JOIN pet_breed b ON b.id = m.pet_breed_id " +
                "WHERE a.id = :alarmId " +
                "LIMIT 1"
    )
    fun getLocalFoodDetailAlarmModelByAlertId(alarmId: Int): LocalFoodDetailAlarmModel?

    @Query("UPDATE alarm SET is_active = :alarmIsActive WHERE id = :alarmId")
    fun switchPetFoodAlarmState(alarmId: Int, alarmIsActive: Boolean): Int
}