package com.example.mypet.data.local.room.dao

import androidx.room.Dao


@Dao
interface LocalFoodDetailDao {
/*    @Query(
        "SELECT " +
                "m.id ${PET_MY_TABLE}_$ID, " +
                "m.avatar_path ${PET_MY_TABLE}_$AVATAR_PATH, " +
                "m.kind_ordinal ${PET_MY_TABLE}_$KIND_ORDINAL, " +
                "m.breed_ordinal ${PET_MY_TABLE}_$BREED_ORDINAL, " +
                "f.id ${PET_FOOD_TABLE}_$ID, " +
                "a.id ${ALARM_TABLE}_$ID, " +
                "a.description ${ALARM_TABLE}_$DESCRIPTION, " +
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
    }*/
}