package com.example.mypet.domain.alarm

import android.media.RingtoneManager
import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.DEFAULT_ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.HOUR
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.MINUTE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_DELAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.IS_VIBRATION
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.RINGTONE_PATH
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.INTERVAL
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_FRIDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_MONDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_SATURDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_SUNDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_THURSDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_TUESDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.IS_WEDNESDAY
import com.example.mypet.data.local.room.entity.LocalRepeatEntity.Companion.TYPE_ORDINAL

data class AlarmModel(
    @ColumnInfo(name = ID)
    val id: Int = DEFAULT_ID,
    @ColumnInfo(name = HOUR)
    val hour: Int = 0,
    @ColumnInfo(name = MINUTE)
    val minute: Int = 0,
    /*    @ColumnInfo(name = DAY)
        val day: Int? = null,
        @ColumnInfo(name = MONTH)
        val month: Int? = null,
        @ColumnInfo(name = YEAR)
        val year: Int? = null,*/

    @ColumnInfo(name = RINGTONE_PATH)
    val ringtonePath: String? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).path,
    @ColumnInfo(name = IS_VIBRATION)
    val isVibration: Boolean? = null,
    @ColumnInfo(name = IS_DELAY)
    val isDelay: Boolean? = null,

    val delayTime: Int? = null,

    @ColumnInfo(name = TYPE_ORDINAL)
    val repeatTypeOrdinal: Int? = null,
    @ColumnInfo(name = INTERVAL)
    val repeatInterval: Int? = null,
    @ColumnInfo(name = IS_MONDAY)
    val isRepeatMonday: Boolean? = null,
    @ColumnInfo(name = IS_TUESDAY)
    val isRepeatTuesday: Boolean? = null,
    @ColumnInfo(name = IS_WEDNESDAY)
    val isRepeatWednesday: Boolean? = null,
    @ColumnInfo(name = IS_THURSDAY)
    val isRepeatThursday: Boolean? = null,
    @ColumnInfo(name = IS_FRIDAY)
    val isRepeatFriday: Boolean? = null,
    @ColumnInfo(name = IS_SATURDAY)
    val isRepeatSaturday: Boolean? = null,
    @ColumnInfo(name = IS_SUNDAY)
    val isRepeatSunday: Boolean? = null,
    /*    @ColumnInfo(name = END_DAY)
        val endDay: Int? = null,
        @ColumnInfo(name = END_MONTH)
        val endMonth: Int? = null,
        @ColumnInfo(name = END_YEAR)
        val endYear: Int? = null,
        @ColumnInfo(name = END_COUNT)
        val endCount: Int? = null,*/
) {
    fun isRepeatable() =
        repeatTypeOrdinal != null
}