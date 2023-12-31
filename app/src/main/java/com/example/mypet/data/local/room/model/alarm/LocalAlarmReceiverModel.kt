package com.example.mypet.data.local.room.model.alarm

import androidx.room.ColumnInfo
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.CARE_TABLE
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.CARE_TYPE_ORDINAL
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.DOSE
import com.example.mypet.data.local.room.entity.LocalCareEntity.Companion.TITLE
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.PET_TABLE

data class LocalAlarmReceiverModel(
    @ColumnInfo(name = "${PET_TABLE}_$ID")
    val petId: Int,
    @ColumnInfo(name = "${PET_TABLE}_$NAME")
    val petName: String,
    @ColumnInfo(name = "${PET_TABLE}_$AVATAR_PATH")
    val petAvatarPath: String?,
    @ColumnInfo(name = "${PET_TABLE}_$KIND_ORDINAL")
    val petKindOrdinal: Int,
    @ColumnInfo(name = "${PET_TABLE}_$BREED_ORDINAL")
    val petBreedOrdinal: Int?,

    @ColumnInfo(name = "${CARE_TABLE}_$CARE_TYPE_ORDINAL")
    val careTypeOrdinal: Int,
    @ColumnInfo(name = "${CARE_TABLE}_$TITLE")
    val careTitle: String?,
    @ColumnInfo(name = "${CARE_TABLE}_$DOSE")
    val careDose: String?,

    @ColumnInfo(name = "${ALARM_TABLE}_$ID")
    val alarmId: Int,
)