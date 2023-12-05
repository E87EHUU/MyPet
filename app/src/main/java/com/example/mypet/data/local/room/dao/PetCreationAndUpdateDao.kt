package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mypet.data.local.room.entity.LocalPetEntity
import com.example.mypet.data.local.room.model.pet.LocalPetModel

@Dao
interface PetCreationAndUpdateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewPetToDb(newPet: LocalPetEntity)

    @Query("SELECT * FROM pet WHERE id = :petId LIMIT 1")
    suspend fun getPetFromDbForUpdateDetails(petId: Int): LocalPetModel

    @Update
    suspend fun updatePetDetailsInDb(updatedPet: LocalPetEntity)
}