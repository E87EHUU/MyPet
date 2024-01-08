package com.example.mypet.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mypet.data.local.room.dao.LocalAlarmDao
import com.example.mypet.data.local.room.dao.LocalAlarmReceiverDao
import com.example.mypet.data.local.room.dao.LocalAlarmServiceDao
import com.example.mypet.data.local.room.dao.LocalBootCompleteDao
import com.example.mypet.data.local.room.dao.LocalCareDao
import com.example.mypet.data.local.room.dao.LocalPetDao
import com.example.mypet.data.local.room.dao.PetCreationAndUpdateDao
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalCareEntity
import com.example.mypet.data.local.room.entity.LocalEndEntity
import com.example.mypet.data.local.room.entity.LocalNoteEntity
import com.example.mypet.data.local.room.entity.LocalPetEntity
import com.example.mypet.data.local.room.entity.LocalRepeatEntity
import com.example.mypet.data.local.room.entity.LocalStartEntity


@Database(
    entities = [
        LocalPetEntity::class,
        LocalCareEntity::class,
        LocalStartEntity::class,
        LocalRepeatEntity::class,
        LocalEndEntity::class,
        LocalAlarmEntity::class,
        LocalNoteEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localPetDao(): LocalPetDao
    abstract fun localCareDao(): LocalCareDao
    abstract fun localAlarmDao(): LocalAlarmDao
    abstract fun localAlarmReceiverDao(): LocalAlarmReceiverDao
    abstract fun localAlarmServiceDao(): LocalAlarmServiceDao
    abstract fun localBootCompleteDao(): LocalBootCompleteDao
    abstract fun localPetCreationDao(): PetCreationAndUpdateDao

    companion object {
        const val ID = "id"

        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val HOUR = "hour"
        const val MINUTE = "minute"

        const val DEFAULT_ID = 0
    }
}