package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mypet.data.local.room.entity.LocalPetBreedEntity
import com.example.mypet.data.local.room.entity.LocalPetKindEntity
import com.example.mypet.data.local.room.entity.LocalPetMyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PetCreationDao {

    @Query("SELECT * FROM pet_breed")
    fun observePetKindList(): Flow<List<LocalPetKindEntity>>

    @Query("SELECT * FROM pet_breed WHERE pet_kind_id = :kindId")
    fun observePetBreedList(kindId: Int): Flow<List<LocalPetBreedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewPetToDb(newPet: LocalPetMyEntity)
}