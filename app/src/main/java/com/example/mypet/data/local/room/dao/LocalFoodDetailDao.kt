package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.TITLE
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
import com.example.mypet.data.local.room.entity.LocalPetFoodEntity.Companion.RATION
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.model.alarm.LocalFoodDetailModel
import com.example.mypet.domain.food.detail.SaveAndSetFoodDetailModel


@Dao
interface LocalFoodDetailDao {
    @Query(
        "SELECT " +
                "f.id $ID, " +
                "m.avatar_path $AVATAR_PATH, " +
                "f.title $TITLE, " +
                "f.ration $RATION, " +
                "m.kind_ordinal $KIND_ORDINAL, " +
                "m.breed_ordinal $BREED_ORDINAL, " +
                "a.id ${ALARM_TABLE}_$ID, " +
                "a.hour ${ALARM_TABLE}_$HOUR, " +
                "a.minute ${ALARM_TABLE}_$MINUTE, " +
                "a.is_repeat_monday ${ALARM_TABLE}_$IS_REPEAT_MONDAY, " +
                "a.is_repeat_tuesday ${ALARM_TABLE}_$IS_REPEAT_TUESDAY, " +
                "a.is_repeat_wednesday ${ALARM_TABLE}_$IS_REPEAT_WEDNESDAY, " +
                "a.is_repeat_thursday ${ALARM_TABLE}_$IS_REPEAT_THURSDAY, " +
                "a.is_repeat_friday ${ALARM_TABLE}_$IS_REPEAT_FRIDAY, " +
                "a.is_repeat_saturday ${ALARM_TABLE}_$IS_REPEAT_SATURDAY, " +
                "a.is_repeat_sunday ${ALARM_TABLE}_$IS_REPEAT_SUNDAY, " +
                "a.ringtone_path ${ALARM_TABLE}_$RINGTONE_PATH, " +
                "a.is_vibration ${ALARM_TABLE}_$IS_VIBRATION, " +
                "a.is_delay ${ALARM_TABLE}_$IS_DELAY, " +
                "a.is_active ${ALARM_TABLE}_$IS_ACTIVE " +
                "FROM pet_food f " +
                "LEFT JOIN alarm a ON a.id = f.alarm_id " +
                "LEFT JOIN pet_my m ON m.id = f.pet_my_id " +
                "WHERE f.id = :foodId " +
                "LIMIT 1"
    )
    fun getLocalFoodDetailModel(foodId: Int): LocalFoodDetailModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFood(localPetFoodEntity: LocalPetFoodEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAlarm(localAlarmEntity: LocalAlarmEntity): Long

    @Query("UPDATE pet_food SET title = :foodName, alarm_id = :alarmId WHERE id = :foodId")
    fun updateFood(foodId: Int, foodName: String?, alarmId: Int): Int

    @Transaction
    fun savePetFoodAndAlarm(saveAndSetFoodDetailModel: SaveAndSetFoodDetailModel): SaveAndSetFoodDetailModel {
        with(saveAndSetFoodDetailModel) {
            val foodId = foodId ?: saveFood(toLocalPetFoodEntity()).toInt()
            if (foodId > 0) {
                val alarmId = saveAlarm(toLocalAlarmEntity()).toInt()

                if (alarmId > 0) {
                    if (updateFood(foodId, foodTitle, alarmId) == 1)
                        return copy(foodId = foodId, alarmId = alarmId)
                    else
                        TODO("Не удалось обновить petFood в room $saveAndSetFoodDetailModel")
                } else TODO("Не удалось записать alarm в room $saveAndSetFoodDetailModel")
            } else TODO("Не удалось записать petFood в room $saveAndSetFoodDetailModel")
        }
    }
}