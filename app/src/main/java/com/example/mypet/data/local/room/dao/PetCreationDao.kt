package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.mypet.data.local.room.entity.LocalPetMyEntity

@Dao
interface PetCreationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewPetToDb(newPet: LocalPetMyEntity)
}