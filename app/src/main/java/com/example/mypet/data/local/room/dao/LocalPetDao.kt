package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AGE
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.WEIGHT
import com.example.mypet.data.local.room.model.LocalAlarmMinModel
import com.example.mypet.data.local.room.model.pet.LocalPetModel
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalPetDao {
    @Query(
        "SELECT " +
                "id $ID, " +
                "avatar_path $AVATAR_PATH, " +
                "name $NAME, " +
                "age $AGE, " +
                "weight $WEIGHT, " +
                "kind_ordinal $KIND_ORDINAL, " +
                "breed_ordinal $BREED_ORDINAL, " +
                "is_active $IS_ACTIVE " +
                "FROM pet"
    )
    fun getLocalPetModels(): Flow<List<LocalPetModel>>

    @Query(
        "SELECT " +
                "a.id, a.hour, a.minute, a.is_active " +
                "FROM pet p " +
                "LEFT JOIN care c ON c.pet_id = p.id AND c.care_type_ordinal = :careFoodTypeOrdinal " +
                "LEFT JOIN alarm a ON a.care_id = c.id " +
                "WHERE p.id = :petId"
    )
    fun getLocalAlarmMinModels(petId: Int, careFoodTypeOrdinal: Int): Flow<List<LocalAlarmMinModel>>

    @Query("DELETE FROM pet WHERE id = :petId")
    suspend fun deletePet(petId: Int)
}