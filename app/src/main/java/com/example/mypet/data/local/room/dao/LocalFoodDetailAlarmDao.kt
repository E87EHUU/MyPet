package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mypet.data.local.room.LocalDatabase
import com.example.mypet.data.local.room.LocalDatabase.Companion.ICON_RES_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_DELAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_FRIDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_MONDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_SATURDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_SUNDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_THURSDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_TUESDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_REPEAT_WEDNESDAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_VIBRATION
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.RINGTONE_PATH
import com.example.mypet.data.local.room.entity.LocalPetFoodEntity
import com.example.mypet.data.local.room.model.pet.LocalFoodDetailAlarmModel
import com.example.mypet.domain.food.detail.alarm.SaveFoodDetailAlarmAndSetAlarm


@Dao
interface LocalFoodDetailAlarmDao {
    @Query(
        "SELECT " +
                "f.id $ID, " +
                "f.title ${LocalDatabase.TITLE}, " +
                "b.icon_res_id ${ICON_RES_ID}, " +
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
                "FROM pet_food f " +
                "LEFT JOIN alarm a ON a.id = f.alarm_id " +
                "LEFT JOIN pet_my m ON m.id = f.pet_my_id " +
                "LEFT JOIN pet_breed b ON b.id = m.pet_breed_id " +
                "WHERE f.id = :foodId " +
                "LIMIT 1"
    )
    fun getLocalFoodDetailAlarmModelByFoodId(foodId: Int): LocalFoodDetailAlarmModel?

    @Query(
        "SELECT " +
                "f.id $ID, " +
                "f.title ${LocalDatabase.TITLE}, " +
                "b.icon_res_id ${ICON_RES_ID}, " +
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
    fun getLocalFoodDetailAlarmModelByAlarmId(alarmId: Int): LocalFoodDetailAlarmModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFood(localPetFoodEntity: LocalPetFoodEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAlarm(localAlarmEntity: LocalAlarmEntity): Long

    @Query("UPDATE pet_food SET title = :foodName, alarm_id = :alarmId WHERE id = :foodId")
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