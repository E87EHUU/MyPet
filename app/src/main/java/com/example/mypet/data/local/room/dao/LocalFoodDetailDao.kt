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
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.DAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.END_COUNT
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.END_DAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.END_MONTH
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.END_YEAR
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
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MONTH
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_INTERVAL
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.REPEAT_TYPE_ORDINAL
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.RINGTONE_PATH
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.YEAR
import com.example.mypet.data.local.room.entity.LocalPetFoodEntity
import com.example.mypet.data.local.room.entity.LocalPetFoodEntity.Companion.RATION
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.PET_FOOD_TABLE
import com.example.mypet.data.local.room.entity.PET_MY_TABLE
import com.example.mypet.domain.food.detail.FoodDetailModel


@Dao
interface LocalFoodDetailDao {
    @Query(
        "SELECT " +
                "m.id ${PET_MY_TABLE}_$ID, " +
                "m.avatar_path ${PET_MY_TABLE}_$AVATAR_PATH, " +
                "m.kind_ordinal ${PET_MY_TABLE}_$KIND_ORDINAL, " +
                "m.breed_ordinal ${PET_MY_TABLE}_$BREED_ORDINAL, " +
                "f.id ${PET_FOOD_TABLE}_$ID, " +
                "f.title ${PET_FOOD_TABLE}_$TITLE, " +
                "f.ration ${PET_FOOD_TABLE}_$RATION, " +
                "a.id ${ALARM_TABLE}_$ID, " +
                "a.hour ${ALARM_TABLE}_$HOUR, " +
                "a.minute ${ALARM_TABLE}_$MINUTE, " +
                "a.day ${ALARM_TABLE}_$DAY, " +
                "a.month ${ALARM_TABLE}_$MONTH, " +
                "a.year ${ALARM_TABLE}_$YEAR, " +
                "a.ringtone_path ${ALARM_TABLE}_$RINGTONE_PATH, " +
                "a.is_vibration ${ALARM_TABLE}_$IS_VIBRATION, " +
                "a.is_delay ${ALARM_TABLE}_$IS_DELAY, " +
                "a.is_active ${ALARM_TABLE}_$IS_ACTIVE, " +
                "a.repeat_type_ordinal ${ALARM_TABLE}_$REPEAT_TYPE_ORDINAL, " +
                "a.repeat_interval ${ALARM_TABLE}_$REPEAT_INTERVAL, " +
                "a.is_repeat_monday ${ALARM_TABLE}_$IS_REPEAT_MONDAY, " +
                "a.is_repeat_tuesday ${ALARM_TABLE}_$IS_REPEAT_TUESDAY, " +
                "a.is_repeat_wednesday ${ALARM_TABLE}_$IS_REPEAT_WEDNESDAY, " +
                "a.is_repeat_thursday ${ALARM_TABLE}_$IS_REPEAT_THURSDAY, " +
                "a.is_repeat_friday ${ALARM_TABLE}_$IS_REPEAT_FRIDAY, " +
                "a.is_repeat_saturday ${ALARM_TABLE}_$IS_REPEAT_SATURDAY, " +
                "a.is_repeat_sunday ${ALARM_TABLE}_$IS_REPEAT_SUNDAY, " +
                "a.end_day ${ALARM_TABLE}_$END_DAY, " +
                "a.end_month ${ALARM_TABLE}_$END_MONTH, " +
                "a.end_year ${ALARM_TABLE}_$END_YEAR, " +
                "a.end_count ${ALARM_TABLE}_$END_COUNT " +
                "FROM pet_food f " +
                "LEFT JOIN alarm a ON a.id = f.alarm_id " +
                "LEFT JOIN pet_my m ON m.id = f.pet_my_id " +
                "WHERE f.id = :foodId " +
                "LIMIT 1"
    )
    fun getLocalFoodDetailModel(foodId: Int): FoodDetailModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFood(localPetFoodEntity: LocalPetFoodEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAlarm(localAlarmEntity: LocalAlarmEntity): Long

    @Transaction
    fun saveFoodDetailModel(foodDetailModel: FoodDetailModel): FoodDetailModel? {
        foodDetailModel.toLocalAlarmEntity()?.let { localAlarmEntity ->
            foodDetailModel.toLocalPetFoodEntity()?.let { localPetFoodEntity ->
                saveAlarm(localAlarmEntity)
                saveFood(localPetFoodEntity)
                return foodDetailModel
            }
        }
        return null
    }
}