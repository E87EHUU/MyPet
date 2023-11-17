package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mypet.data.local.room.entity.LocalPetBreedEntity
import com.example.mypet.data.local.room.entity.LocalPetKindEntity
import com.example.mypet.data.local.room.entity.LocalPetMyEntity
import com.example.mypet.data.local.room.model.LocalPetModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow

@Dao
interface PetCreationDao {

    @Query("SELECT * FROM PET_KIND")
    fun observePetKindList(): Flow<List<LocalPetKindEntity>>

    @Query("SELECT * FROM PET_BREED WHERE kind_id = :kindId")
    fun observePetBreedList(kindId: Int): Flow<List<LocalPetBreedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewPetToDb(newPet: LocalPetMyEntity)
}