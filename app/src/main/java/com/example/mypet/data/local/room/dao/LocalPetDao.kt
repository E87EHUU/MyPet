package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.BREED_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.DATE_OF_BIRTH
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.KIND_ORDINAL
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.SEX
import com.example.mypet.data.local.room.entity.LocalPetEntity.Companion.WEIGHT
import com.example.mypet.data.local.room.model.LocalAlarmMinModel
import com.example.mypet.data.local.room.model.pet.LocalPetCareFoodModel
import com.example.mypet.data.local.room.model.pet.LocalPetCareModel
import com.example.mypet.data.local.room.model.pet.LocalPetModel
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalPetDao {
    @Query(
        "SELECT " +
                "id $ID, " +
                "avatar_path $AVATAR_PATH, " +
                "name $NAME, " +
                "date_of_birth $DATE_OF_BIRTH, " +
                "weight $WEIGHT, " +
                "kind_ordinal $KIND_ORDINAL, " +
                "breed_ordinal $BREED_ORDINAL, " +
                "sex $SEX, " +
                "is_active $IS_ACTIVE " +
                "FROM pet "
    )
    fun getLocalPetModels(): Flow<List<LocalPetModel>>

    @Query(
        "SELECT " +
                "c.id " +
                "FROM care c " +
                "WHERE c.pet_id = :petId AND c.care_type_ordinal = :careFoodTypeOrdinal"
    )
    fun getLocalPetCareFoodModel(petId: Int, careFoodTypeOrdinal: Int): Flow<LocalPetCareFoodModel?>

    @Query(
        "SELECT " +
                "a.id, a.hour, a.minute, a.is_active " +
                "FROM pet p " +
                "JOIN care c ON c.pet_id = p.id AND c.care_type_ordinal = :careFoodTypeOrdinal " +
                "JOIN alarm a ON a.care_id = c.id " +
                "WHERE p.id = :petId"
    )
    fun getLocalAlarmMinModels(petId: Int, careFoodTypeOrdinal: Int): Flow<List<LocalAlarmMinModel>>

    @Query(
        "SELECT " +
                "c.id, c.care_type_ordinal, a.before_start, a.next_start " +
                "FROM care c " +
                "LEFT JOIN alarm a ON a.id = (SELECT id FROM alarm WHERE care_id = c.id AND a.next_start > :currentTimeInMillis LIMIT 1) " +
                "WHERE c.pet_id = :petId AND c.care_type_ordinal != 0"
    )
    fun getLocalPetCareModels(petId: Int, currentTimeInMillis: Long): Flow<List<LocalPetCareModel>>

    @Query("SELECT id FROM care WHERE pet_id = :petId")
    fun getPetCareIds(petId: Int): List<Int>

    @Query("DELETE FROM start WHERE care_id IN (:careIds)")
    fun deleteStarts(careIds: List<Int>)

    @Query("DELETE FROM repeat WHERE care_id IN (:careIds)")
    fun deleteRepeats(careIds: List<Int>)

    @Query("DELETE FROM alarm WHERE care_id IN (:careIds)")
    fun deleteAlarms(careIds: List<Int>)

    @Query("DELETE FROM care WHERE id IN (:careIds)")
    fun deleteCares(careIds: List<Int>)

    @Query("DELETE FROM pet WHERE id = :petId")
    suspend fun deleteLocalPetEntities(petId: Int)

    @Transaction
    suspend fun deletePet(petId: Int) {
        val petCareIds = getPetCareIds(petId)

        if (petCareIds.isNotEmpty()) {
            deleteStarts(petCareIds)
            deleteRepeats(petCareIds)
            deleteAlarms(petCareIds)
            deleteCares(petCareIds)
        }

        deleteLocalPetEntities(petId)
    }
}