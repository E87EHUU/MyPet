package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AGE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR_PATH
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.IS_ACTIVE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.WEIGHT
import com.example.mypet.data.local.room.entity.PET_BREED_TABLE
import com.example.mypet.data.local.room.entity.PET_KIND_TABLE
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
                "m.is_active $IS_ACTIVE, " +
                "k.id ${PET_KIND_TABLE}_$ID, " +
                "b.id ${PET_BREED_TABLE}_$ID " +
                "FROM pet_my m " +
                "LEFT JOIN pet_kind k ON k.id = m.pet_kind_id " +
                "LEFT JOIN pet_breed b ON b.id = m.pet_breed_id " +
                "WHERE " +
                "CASE " +
                "WHEN :activePetId IS NULL THEN m.is_active = 1 " +
                "ELSE m.id = :activePetId " +
                "END"
    )
    fun observePetList(activePetId: Int?): Flow<List<LocalPetModel>>
}