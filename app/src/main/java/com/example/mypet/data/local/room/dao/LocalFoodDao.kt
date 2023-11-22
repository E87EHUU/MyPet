package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.TITLE
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.PET_FOOD_TABLE
import com.example.mypet.data.local.room.model.pet.LocalFoodModel
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalFoodDao {
    @Query(
        "SELECT " +
                "f.id ${PET_FOOD_TABLE}_$ID, " +
                "f.title $TITLE, " +
                "a.id ${ALARM_TABLE}_$ID, " +
                "a.hour ${ALARM_TABLE}_$HOUR, " +
                "a.minute ${ALARM_TABLE}_$MINUTE, " +
                "a.is_active ${ALARM_TABLE}_$IS_ACTIVE " +
                "FROM pet_food f " +
                "LEFT JOIN alarm a ON a.id = f.alarm_id " +
                "WHERE f.pet_my_id = :petMyId"
    )
    fun observeFoodModels(petMyId: Int): Flow<List<LocalFoodModel>>

/*    @Query(
        "SELECT " +
                "f.id $ID, " +
                "f.title $TITLE, " +
                "m.kind_ordinal $KIND_ORDINAL, " +
                "m.breed_ordinal $BREED_ORDINAL, " +
                "a.id ${ALARM_TABLE}_${ID}, " +
                "a.hour $HOUR, " +
                "a.minute $MINUTE, " +
                "a.is_repeat_monday $IS_REPEAT_MONDAY, " +
                "a.is_repeat_tuesday $IS_REPEAT_TUESDAY, " +
                "a.is_repeat_wednesday $IS_REPEAT_WEDNESDAY, " +
                "a.is_repeat_thursday $IS_REPEAT_THURSDAY, " +
                "a.is_repeat_friday $IS_REPEAT_FRIDAY, " +
                "a.is_repeat_saturday $IS_REPEAT_SATURDAY, " +
                "a.is_repeat_sunday $IS_REPEAT_SUNDAY, " +
                "a.ringtone_path $RINGTONE_PATH, " +
                "a.is_vibration $IS_VIBRATION, " +
                "a.is_delay $IS_DELAY, " +
                "a.is_active $IS_ACTIVE " +
                "FROM alarm a " +
                "LEFT JOIN pet_food f ON f.alarm_id = a.id " +
                "LEFT JOIN pet_my m ON m.id = f.pet_my_id " +
                "WHERE a.id = :alarmId " +
                "LIMIT 1"
    )
    fun getLocalFoodAlarmModel(alarmId: Int): LocalAlarmServiceModel?

*/
}