package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mypet.data.local.room.entity.LocalPetMyEntity
import com.example.mypet.data.local.room.model.pet.LocalPetModel

@Dao
interface PetCreationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewPetToDb(newPet: LocalPetMyEntity)

    @Query("SELECT * FROM pet_my WHERE id = :petId LIMIT 1")
    suspend fun getPetFromDbForUpdateDetails(petId: Int): LocalPetModel

    @Update
    suspend fun updatePetDetailsInDb(updatedPet: LocalPetMyEntity)
}