package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mypet.data.local.room.LocalDatabase
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalPetFoodEntity
import com.example.mypet.data.local.room.model.pet.LocalFoodAlarmModel
import com.example.mypet.domain.food.detail.alarm.SaveFoodDetailAlarmAndSetAlarm


@Dao
interface LocalFoodDetailAlarmDao {
    @Query(
        "SELECT " +
                "f.id ${LocalDatabase.ID}, " +
                "f.title ${LocalDatabase.TITLE}, " +
                "b.icon_path ${LocalDatabase.ICON_PATH}, " +
                "a.id ${ALARM_TABLE}_${LocalDatabase.ID}, " +
                "a.hour ${LocalAlarmEntity.HOUR}, " +
                "a.minute ${LocalAlarmEntity.MINUTE}, " +
                "a.is_repeat_monday ${LocalAlarmEntity.IS_REPEAT_MONDAY}, " +
                "a.is_repeat_tuesday ${LocalAlarmEntity.IS_REPEAT_TUESDAY}, " +
                "a.is_repeat_wednesday ${LocalAlarmEntity.IS_REPEAT_WEDNESDAY}, " +
                "a.is_repeat_thursday ${LocalAlarmEntity.IS_REPEAT_THURSDAY}, " +
                "a.is_repeat_friday ${LocalAlarmEntity.IS_REPEAT_FRIDAY}, " +
                "a.is_repeat_saturday ${LocalAlarmEntity.IS_REPEAT_SATURDAY}, " +
                "a.is_repeat_sunday ${LocalAlarmEntity.IS_REPEAT_SUNDAY}, " +
                "a.ringtone_path ${LocalAlarmEntity.RINGTONE_PATH}, " +
                "a.is_vibration ${LocalAlarmEntity.IS_VIBRATION}, " +
                "a.is_delay ${LocalAlarmEntity.IS_DELAY}, " +
                "a.is_active ${LocalAlarmEntity.IS_ACTIVE} " +
                "FROM pet_food f " +
                "LEFT JOIN alarm a ON a.id = f.alarm_id " +
                "LEFT JOIN pet_my m ON m.id = f.pet_my_id " +
                "LEFT JOIN pet_breed b ON b.id = m.pet_breed_id " +
                "WHERE f.id = :foodId " +
                "LIMIT 1"
    )
    fun getLocalFoodAlarmModelByFoodId(foodId: Int): LocalFoodAlarmModel?

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