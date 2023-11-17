package com.example.mypet.data

import com.example.mypet.data.local.room.dao.PetCreationDao
import com.example.mypet.data.local.room.entity.LocalPetBreedEntity
import com.example.mypet.data.local.room.entity.LocalPetKindEntity
import com.example.mypet.data.local.room.entity.LocalPetMyEntity
import com.example.mypet.data.local.room.model.LocalPetBreedModel
import com.example.mypet.data.local.room.model.LocalPetKindModel
import com.example.mypet.domain.PetCreationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PetCreationRepositoryImpl @Inject constructor(
    private val petCreationDao: PetCreationDao
) : PetCreationRepository {

    override fun getKindList(): Flow<List<LocalPetKindModel>> {
        return petCreationDao.observePetKindList().map { petKindList ->
            petKindList.map {
                it.toLocalPetKindModel()
            }
        }
    }

    override fun getBreedList(kindId: Int): Flow<List<LocalPetBreedModel>> {
        return petCreationDao.observePetBreedList(kindId).map { petKindList ->
            petKindList.map {
                it.toLocalPetBreedModel()
            }
        }
    }

    override suspend fun addNewPetToDb(
        name: String,
        kindId: Int,
        breedId: Int,
        dateOfBirth: String,
        weight: Int
    ) {
        petCreationDao.addNewPetToDb(
            LocalPetMyEntity(
                id = generateUniqueId(),
                kindId = kindId,
                breedId = breedId,
                avatarPath = null,
                name = name,
                age = 1,
                weight = weight,
                sex = 1,
                isActive = true
            )
        )
    }

    private fun LocalPetKindEntity.toLocalPetKindModel() =
        LocalPetKindModel(
            id = id,
            kindName = "name"
        )

    private fun LocalPetBreedEntity.toLocalPetBreedModel() =
        LocalPetBreedModel(
            breedName = "name"
        )

    companion object {
        private var counter = 0

        fun generateUniqueId(): Int {
            return counter++
        }
    }
}