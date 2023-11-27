package com.example.mypet.domain.food.detail

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
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
import com.example.mypet.domain.alarm.AlarmModel

data class FoodDetailModel(
    @ColumnInfo(name = "${PET_MY_TABLE}_$ID")
    val petId: Int,
    @ColumnInfo(name = "${PET_MY_TABLE}_$AVATAR_PATH")
    val petAvatarPath: String? = null,
    @ColumnInfo(name = "${PET_MY_TABLE}_$KIND_ORDINAL")
    val petKindOrdinal: Int,
    @ColumnInfo(name = "${PET_MY_TABLE}_$BREED_ORDINAL")
    val petBreedOrdinal: Int? = null,

    @ColumnInfo(name = "${PET_FOOD_TABLE}_$ID")
    val foodId: Int? = null,
    @ColumnInfo(name = "${PET_FOOD_TABLE}_$TITLE")
    val foodTitle: String? = null,
    @ColumnInfo(name = "${PET_FOOD_TABLE}_$RATION")
    val foodRation: String? = null,

    @ColumnInfo(name = "${ALARM_TABLE}_$ID")
    val alarmId: Int? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$HOUR")
    val alarmHour: Int? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$MINUTE")
    val alarmMinute: Int? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$DAY")
    val alarmDay: Int? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$MONTH")
    val alarmMonth: Int? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$YEAR")
    val alarmYear: Int? = null,

    @ColumnInfo(name = "${ALARM_TABLE}_$RINGTONE_PATH")
    val alarmRingtonePath: String? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_VIBRATION")
    val alarmIsVibration: Boolean? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_DELAY")
    val alarmIsDelay: Boolean? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_ACTIVE")
    val alarmIsActive: Boolean? = true,

    @ColumnInfo(name = "${ALARM_TABLE}_$REPEAT_TYPE_ORDINAL")
    val alarmRepeatTypeOrdinal: Int? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$REPEAT_INTERVAL")
    val alarmRepeatInterval: Int? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_MONDAY")
    val alarmIsRepeatMonday: Boolean? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_TUESDAY")
    val alarmIsRepeatTuesday: Boolean? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_WEDNESDAY")
    val alarmIsRepeatWednesday: Boolean? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_THURSDAY")
    val alarmIsRepeatThursday: Boolean? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_FRIDAY")
    val alarmIsRepeatFriday: Boolean? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_SATURDAY")
    val alarmIsRepeatSaturday: Boolean? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$IS_REPEAT_SUNDAY")
    val alarmIsRepeatSunday: Boolean? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$END_DAY")
    val alarmEndDay: Int? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$END_MONTH")
    val alarmEndMonth: Int? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$END_YEAR")
    val alarmEndYear: Int? = null,
    @ColumnInfo(name = "${ALARM_TABLE}_$END_COUNT")
    val alarmEndCount: Int? = null,
) {
    fun toAlarmModel(): AlarmModel? {
        val alarmId = alarmId ?: return null
        val alarmHour = alarmHour ?: return null
        val alarmMinute = alarmMinute ?: return null

        return AlarmModel(
            id = alarmId,
            hour = alarmHour,
            minute = alarmMinute,
            day = alarmDay,
            month = alarmMonth,
            year = alarmYear,
            ringtonePath = alarmRingtonePath,
            isVibration = alarmIsVibration,
            isDelay = alarmIsDelay,
            delayTime = null,
            repeatTypeOrdinal = alarmRepeatTypeOrdinal,
            repeatInterval = alarmRepeatInterval,
            isRepeatMonday = alarmIsRepeatMonday,
            isRepeatTuesday = alarmIsRepeatTuesday,
            isRepeatWednesday = alarmIsRepeatWednesday,
            isRepeatThursday = alarmIsRepeatThursday,
            isRepeatFriday = alarmIsRepeatFriday,
            isRepeatSaturday = alarmIsRepeatSaturday,
            isRepeatSunday = alarmIsRepeatSunday,
            endDay = alarmEndDay,
            endMonth = alarmEndMonth,
            endYear = alarmEndYear,
            endCount = alarmEndCount,
        )
    }

    fun toLocalPetFoodEntity(): LocalPetFoodEntity? {
        val alarmId = alarmId ?: return null
        val foodTitle = foodTitle ?: return null

        return LocalPetFoodEntity(
            id = foodId ?: DEFAULT_ID,
            petMyId = petId,
            alarmId = alarmId,
            title = foodTitle,
            ration = foodRation
        )
    }

    fun toLocalAlarmEntity(): LocalAlarmEntity? {
        val alarmHour = alarmHour ?: return null
        val alarmMinute = alarmMinute ?: return null

        return LocalAlarmEntity(
            id = alarmId ?: DEFAULT_ID,
            hour = alarmHour,
            minute = alarmMinute,
            day = alarmDay,
            month = alarmMonth,
            year = alarmYear,
            ringtonePath = alarmRingtonePath,
            isVibration = alarmIsVibration,
            isDelay = alarmIsDelay,
            repeatTypeOrdinal = alarmRepeatTypeOrdinal,
            repeatInterval = alarmRepeatInterval,
            isRepeatMonday = alarmIsRepeatMonday,
            isRepeatTuesday = alarmIsRepeatTuesday,
            isRepeatWednesday = alarmIsRepeatWednesday,
            isRepeatThursday = alarmIsRepeatThursday,
            isRepeatFriday = alarmIsRepeatFriday,
            isRepeatSaturday = alarmIsRepeatSaturday,
            isRepeatSunday = alarmIsRepeatSunday,
            endDay = alarmEndDay,
            endMonth = alarmEndMonth,
            endYear = alarmEndYear,
            endCount = alarmEndCount,
        )
    }
}