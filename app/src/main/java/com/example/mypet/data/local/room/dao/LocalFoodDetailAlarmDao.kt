package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_DELAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_VIBRATION
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MELODY_URI
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_FRIDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_MONDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_SATURDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_SUNDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_THURSDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_TUESDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_WEDNESDAY
import com.example.mypet.data.local.room.entity.LocalPetFoodEntity
import com.example.mypet.data.local.room.model.pet.LocalFoodDetailAlarmModel
import com.example.mypet.domain.food.detail.alarm.SaveFoodDetailAlarmAndSetAlarm


@Dao
interface LocalFoodDetailAlarmDao {
    @Query(
        "SELECT " +
                "f.id $ID, " +
                "f.name $NAME, " +
                "a.id ${ALARM_TABLE}_${ID}, " +
                "a.hour ${ALARM_TABLE}_${HOUR}, " +
                "a.minute ${ALARM_TABLE}_${MINUTE}, " +
                "a.repeat_monday ${ALARM_TABLE}_${REPEAT_MONDAY}, " +
                "a.repeat_tuesday ${ALARM_TABLE}_${REPEAT_TUESDAY}, " +
                "a.repeat_wednesday ${ALARM_TABLE}_${REPEAT_WEDNESDAY}, " +
                "a.repeat_thursday ${ALARM_TABLE}_${REPEAT_THURSDAY}, " +
                "a.repeat_friday ${ALARM_TABLE}_${REPEAT_FRIDAY}, " +
                "a.repeat_saturday ${ALARM_TABLE}_${REPEAT_SATURDAY}, " +
                "a.repeat_sunday ${ALARM_TABLE}_${REPEAT_SUNDAY}, " +
                "a.melody_uri ${ALARM_TABLE}_${MELODY_URI}, " +
                "a.is_vibration ${ALARM_TABLE}_${IS_VIBRATION}, " +
                "a.is_delay ${ALARM_TABLE}_${IS_DELAY}, " +
                "a.is_active ${ALARM_TABLE}_${IS_ACTIVE} " +
                "FROM pet_food f " +
                "LEFT JOIN alarm a ON a.id = f.alarm_id " +
                "WHERE f.id = :foodId " +
                "LIMIT 1"
    )
    fun getLocalFoodDetailAlarmModel(foodId: Int): LocalFoodDetailAlarmModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFood(localPetFoodEntity: LocalPetFoodEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAlarm(localAlarmEntity: LocalAlarmEntity): Long

    @Query("UPDATE pet_food SET name = :foodName, alarm_id = :alarmId WHERE id = :foodId")
    fun updateFood(foodId: Int, foodName: String?, alarmId: Int): Int

    @Transaction
    fun savePetFoodAndAlarm(saveFoodDetailAlarmAndSetAlarm: SaveFoodDetailAlarmAndSetAlarm): SaveFoodDetailAlarmAndSetAlarm {
        with(saveFoodDetailAlarmAndSetAlarm) {
            val foodId = foodId ?: saveFood(toLocalPetFoodEntity()).toInt()
            if (foodId > 0) {
                val alarmId = saveAlarm(toLocalAlarmEntity()).toInt()

                if (alarmId > 0) {
                    if (updateFood(foodId, foodName, alarmId) == 1)
                        return copy(foodId = foodId, alarmId = alarmId)
                    else
                        TODO("Не удалось обновить petFood в room $saveFoodDetailAlarmAndSetAlarm")
                } else TODO("Не удалось записать alarm в room $saveFoodDetailAlarmAndSetAlarm")
            } else TODO("Не удалось записать petFood в room $saveFoodDetailAlarmAndSetAlarm")
        }
    }
}