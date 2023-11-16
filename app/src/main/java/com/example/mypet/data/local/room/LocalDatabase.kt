package com.example.mypet.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mypet.data.local.room.dao.LocalBootCompleteDao
import com.example.mypet.data.local.room.dao.LocalFoodAlarmDao
import com.example.mypet.data.local.room.dao.LocalFoodAlarmServiceDao
import com.example.mypet.data.local.room.dao.LocalFoodDao
import com.example.mypet.data.local.room.dao.LocalPetDao
import com.example.mypet.data.local.room.entity.LocalAlarmEntity
import com.example.mypet.data.local.room.entity.LocalPetBreedEntity
import com.example.mypet.data.local.room.entity.LocalPetFoodEntity
import com.example.mypet.data.local.room.entity.LocalPetKindEntity
import com.example.mypet.data.local.room.entity.LocalPetMyEntity

@Database(
    entities = [
        LocalPetMyEntity::class,
        LocalPetKindEntity::class,
        LocalPetBreedEntity::class,
        LocalPetFoodEntity::class,
        LocalAlarmEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localPetDao(): LocalPetDao
    abstract fun localFoodDao(): LocalFoodDao
    abstract fun localFoodAlarmDao(): LocalFoodAlarmDao
    abstract fun localFoodAlarmServiceDao(): LocalFoodAlarmServiceDao
    abstract fun localBootCompleteDao(): LocalBootCompleteDao

    companion object {
        const val ID = "id"

        const val NAME = "name"
        const val TITLE = "title"
        const val ICON_PATH = "icon_path"

        const val DEFAULT_ID = 0
    }
}