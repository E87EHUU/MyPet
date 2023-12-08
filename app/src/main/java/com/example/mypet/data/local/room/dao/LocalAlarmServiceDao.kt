package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.LocalDatabase.Companion.DESCRIPTION
import com.example.mypet.data.local.room.LocalDatabase.Companion.HOUR
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.MINUTE
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_DELAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_VIBRATION
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.RINGTONE_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_FRIDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_MONDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_SATURDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_SUNDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_THURSDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_TUESDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_WEDNESDAY
import com.example.mypet.data.local.room.entity.PET_TABLE
import com.example.mypet.data.local.room.entity.REPEAT_TABLE
import com.example.mypet.domain.alarm.service.AlarmServiceModel


@Dao
interface LocalAlarmServiceDao {
    @Query(
        "SELECT " +
                "p.id ${PET_TABLE}_$ID, " +
                "p.avatar_path ${PET_TABLE}_$AVATAR_PATH, " +
                "p.kind_ordinal ${PET_TABLE}_$KIND_ORDINAL, " +
                "p.breed_ordinal ${PET_TABLE}_$BREED_ORDINAL, " +
                "a.id ${ALARM_TABLE}_$ID, " +
                "a.description ${ALARM_TABLE}_$DESCRIPTION, " +
                "a.hour ${ALARM_TABLE}_$HOUR, " +
                "a.minute ${ALARM_TABLE}_$MINUTE, " +
/*                "a.day ${ALARM_TABLE}_$DAY, " +
                "a.month ${ALARM_TABLE}_$MONTH, " +
                "a.year ${ALARM_TABLE}_$YEAR, " +*/
                "a.ringtone_path ${ALARM_TABLE}_$RINGTONE_PATH, " +
                "a.is_vibration ${ALARM_TABLE}_$IS_VIBRATION, " +
                "a.is_delay ${ALARM_TABLE}_$IS_DELAY, " +
                "a.is_active ${ALARM_TABLE}_$IS_ACTIVE, " +
                "r.is_monday ${REPEAT_TABLE}_$IS_MONDAY, " +
                "r.is_tuesday ${REPEAT_TABLE}_$IS_TUESDAY, " +
                "r.is_wednesday ${REPEAT_TABLE}_$IS_WEDNESDAY, " +
                "r.is_thursday ${REPEAT_TABLE}_$IS_THURSDAY, " +
                "r.is_friday ${REPEAT_TABLE}_$IS_FRIDAY, " +
                "r.is_saturday ${REPEAT_TABLE}_$IS_SATURDAY, " +
                "r.is_sunday ${REPEAT_TABLE}_$IS_SUNDAY " +
/*                "a.end_day ${ALARM_TABLE}_$END_DAY, " +
                "a.end_month ${ALARM_TABLE}_$END_MONTH, " +
                "a.end_year ${ALARM_TABLE}_$END_YEAR, " +
                "a.end_count ${ALARM_TABLE}_$END_COUNT " +*/
                "FROM alarm a " +
                "LEFT JOIN care c ON c.id = a.care_id " +
                "LEFT JOIN pet p ON p.id = c.pet_id " +
                "LEFT JOIN repeat r ON r.id = c.pet_id " +
                "WHERE a.id = :alarmId " +
                "LIMIT 1"
    )
    fun getLocalAlarmServiceModel(alarmId: Int): AlarmServiceModel?

    @Query("UPDATE alarm SET is_active = 0 WHERE id = :alarmId")
    fun disableAlarm(alarmId: Int)
}