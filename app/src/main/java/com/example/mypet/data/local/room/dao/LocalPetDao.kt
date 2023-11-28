package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.ALARM_TABLE
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.HOUR
import com.example.mypet.data.local.room.entity.LocalAlarmEntity.Companion.MINUTE
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AGE
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.WEIGHT
import com.example.mypet.data.local.room.model.pet.LocalPetFoodAlarmModel
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
                "a.id ${ALARM_TABLE}_$ID, " +
                "a.hour ${ALARM_TABLE}_$HOUR, " +
                "a.minute ${ALARM_TABLE}_$MINUTE, " +
                "a.is_active ${ALARM_TABLE}_$IS_ACTIVE " +
                "FROM care c " +
                "LEFT JOIN alarm a ON a.id = c.pet_id " +
                "WHERE c.pet_id = :petId AND c.care_type_ordinal = :careFoodTypeOrdinal"
    )
    fun getLocalPetFoodModel(petId: Int, careFoodTypeOrdinal: Int): Flow<List<LocalPetFoodAlarmModel>>

    @Query("DELETE FROM pet WHERE id = :petId")
    suspend fun deletePet(petId: Int)
}