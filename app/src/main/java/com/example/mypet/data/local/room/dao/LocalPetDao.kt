package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AGE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.WEIGHT
import com.example.mypet.data.local.room.model.pet.LocalPetModel
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalPetDao {
    @Query(
        "SELECT " +
                "m.id $ID, " +
                "m.avatar_path $AVATAR_PATH, " +
                "m.name $NAME, " +
                "m.age $AGE, " +
                "m.weight $WEIGHT, " +
                "m.kind_ordinal $KIND_ORDINAL, " +
                "m.breed_ordinal $BREED_ORDINAL, " +
                "m.is_active $IS_ACTIVE " +
                "FROM pet_my m"
    )
    fun getPetList(): Flow<List<LocalPetModel>>

    @Query("DELETE FROM pet_my WHERE id = :petId")
    suspend fun deletePet(petId: Int)
}