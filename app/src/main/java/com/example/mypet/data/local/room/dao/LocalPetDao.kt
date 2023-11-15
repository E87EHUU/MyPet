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
                "k.name ${PET_KIND_TABLE}_$NAME, " +
                "b.name ${PET_BREED_TABLE}_$NAME " +
                "FROM pet_my m " +
                "LEFT JOIN pet_kind k ON k.id = m.pet_kind_id " +
                "LEFT JOIN pet_breed b ON b.id = m.pet_breed_id "
    )
    fun observePetList(): Flow<List<LocalPetModel>>
}