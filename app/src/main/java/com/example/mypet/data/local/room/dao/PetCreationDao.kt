package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.entity.LocalPetBreedEntity
import com.example.mypet.data.local.room.entity.LocalPetKindEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PetCreationDao {

    @Query("SELECT * FROM PET_KIND")
    fun observePetKindList(): Flow<List<LocalPetKindEntity>>

    @Query("SELECT * FROM PET_BREED WHERE kind_id = :kindId")
    fun observePetBreedList(kindId: Int): Flow<List<LocalPetBreedEntity>>
}