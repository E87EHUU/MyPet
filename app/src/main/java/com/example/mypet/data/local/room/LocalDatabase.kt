package com.example.mypet.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mypet.data.local.room.dao.LocalPetDetailDao
import com.example.mypet.data.local.room.entity.LocalPetBreedEntity
import com.example.mypet.data.local.room.entity.LocalPetMyEntity

@Database(
    entities = [
        LocalPetMyEntity::class,
        LocalPetBreedEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localPetDetailDao(): LocalPetDetailDao

    companion object {
        const val ID = "id"
        const val BREED_ID = "breed_id"

        const val DEFAULT_ID = 0
    }
}