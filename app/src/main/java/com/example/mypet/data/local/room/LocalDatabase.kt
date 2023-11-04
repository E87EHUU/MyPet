package com.example.mypet.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mypet.data.local.room.dao.LocalFoodDetailAlarmDao
import com.example.mypet.data.local.room.dao.LocalPetDetailDao
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
    abstract fun localPetDetailDao(): LocalPetDetailDao
    abstract fun localFoodDetailAlarmDao(): LocalFoodDetailAlarmDao

    companion object {
        const val ID = "id"
        const val PET_MY_ID ="pet_my_id"
        const val KIND_ID = "kind_id"
        const val BREED_ID = "breed_id"
        const val FOOD_ID = "food_id"

        const val NAME = "name"

        const val DEFAULT_ID = 0
    }
}