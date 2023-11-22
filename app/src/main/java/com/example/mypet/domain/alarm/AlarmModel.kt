package com.example.mypet.domain.alarm

import android.media.RingtoneManager
import android.os.Parcel
import android.os.Parcelable


data class AlarmModel(
    var ringtonePath: String? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).path,
    var isVibration: Boolean = true,
    var isDelay: Boolean = true,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ringtonePath)
        parcel.writeByte(if (isVibration) 1 else 0)
        parcel.writeByte(if (isDelay) 1 else 0)
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