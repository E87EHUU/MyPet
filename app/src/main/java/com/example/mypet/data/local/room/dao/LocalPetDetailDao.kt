package com.example.mypet.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mypet.data.local.room.LocalDatabase.Companion.ID
import com.example.mypet.data.local.room.LocalDatabase.Companion.NAME
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AGE
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.AVATAR
import com.example.mypet.data.local.room.entity.LocalPetMyEntity.Companion.WEIGHT
import com.example.mypet.data.local.room.model.LocalPetModel
import com.example.mypet.data.local.room.model.LocalPetModel.Companion.BREED_NAME
import kotlinx.coroutines.flow.Flow


@Dao
interface LocalPetDetailDao {
    @Query(
        "SELECT " +
                "m.id $ID, " +
                "m.avatar $AVATAR, " +
                "m.name $NAME, " +
                "m.age $AGE, " +
                "m.weight $WEIGHT, " +
                "b.name $BREED_NAME " +
                "FROM pet_my m " +
                "LEFT JOIN pet_breed b ON b.id = m.breed_id " +
                "WHERE m.is_active = 1 " +
                "LIMIT 1"
    )
    fun observeActivePet(): Flow<LocalPetModel?>
}