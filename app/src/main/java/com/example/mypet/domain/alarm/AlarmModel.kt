package com.example.mypet.domain.alarm

import android.media.RingtoneManager
import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.DAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.END_COUNT
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.END_DAY
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.END_MONTH
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.END_YEAR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
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

data class AlarmModel(
    @ColumnInfo(name = ID)
    val id: Int,
    @ColumnInfo(name = HOUR)
    val hour: Int,
    @ColumnInfo(name = MINUTE)
    val minute: Int,
    @ColumnInfo(name = DAY)
    val day: Int? = null,
    @ColumnInfo(name = MONTH)
    val month: Int? = null,
    @ColumnInfo(name = YEAR)
    val year: Int? = null,

    @ColumnInfo(name = RINGTONE_PATH)
    val ringtonePath: String? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).path,
    @ColumnInfo(name = IS_VIBRATION)
    val isVibration: Boolean? = null,
    @ColumnInfo(name = IS_DELAY)
    val isDelay: Boolean? = null,

    val delayTime: Int? = null,

    @ColumnInfo(name = REPEAT_TYPE_ORDINAL)
    val repeatTypeOrdinal: Int? = null,
    @ColumnInfo(name = REPEAT_INTERVAL)
    val repeatInterval: Int? = null,
    @ColumnInfo(name = IS_REPEAT_MONDAY)
    val isRepeatMonday: Boolean? = null,
    @ColumnInfo(name = IS_REPEAT_TUESDAY)
    val isRepeatTuesday: Boolean? = null,
    @ColumnInfo(name = IS_REPEAT_WEDNESDAY)
    val isRepeatWednesday: Boolean? = null,
    @ColumnInfo(name = IS_REPEAT_THURSDAY)
    val isRepeatThursday: Boolean? = null,
    @ColumnInfo(name = IS_REPEAT_FRIDAY)
    val isRepeatFriday: Boolean? = null,
    @ColumnInfo(name = IS_REPEAT_SATURDAY)
    val isRepeatSaturday: Boolean? = null,
    @ColumnInfo(name = IS_REPEAT_SUNDAY)
    val isRepeatSunday: Boolean? = null,
    @ColumnInfo(name = END_DAY)
    val endDay: Int? = null,
    @ColumnInfo(name = END_MONTH)
    val endMonth: Int? = null,
    @ColumnInfo(name = END_YEAR)
    val endYear: Int? = null,
    @ColumnInfo(name = END_COUNT)
    val endCount: Int? = null,
) : Parcelable {
    fun isRepeatable() =
        repeatTypeOrdinal != null

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(hour)
        parcel.writeInt(minute)
        parcel.writeValue(day)
        parcel.writeValue(month)
        parcel.writeValue(year)
        parcel.writeString(ringtonePath)
        parcel.writeValue(isVibration)
        parcel.writeValue(isDelay)
        parcel.writeValue(delayTime)
        parcel.writeValue(repeatTypeOrdinal)
        parcel.writeValue(repeatInterval)
        parcel.writeValue(isRepeatMonday)
        parcel.writeValue(isRepeatTuesday)
        parcel.writeValue(isRepeatWednesday)
        parcel.writeValue(isRepeatThursday)
        parcel.writeValue(isRepeatFriday)
        parcel.writeValue(isRepeatSaturday)
        parcel.writeValue(isRepeatSunday)
        parcel.writeValue(endDay)
        parcel.writeValue(endMonth)
        parcel.writeValue(endYear)
        parcel.writeValue(endCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlarmModel> {
        override fun createFromParcel(parcel: Parcel): AlarmModel {
            return AlarmModel(parcel)
        }

        override fun newArray(size: Int): Array<AlarmModel?> {
            return arrayOfNulls(size)
        }
    }
}