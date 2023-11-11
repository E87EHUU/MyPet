package com.example.mypet.data

import com.example.mypet.data.local.room.dao.LocalPetDetailDao
import com.example.mypet.domain.PetCreationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PetCreationRepositoryImpl @Inject constructor(
    private val localPetDetailDao: LocalPetDetailDao
) : PetCreationRepository {

    override fun getKindList(): Flow<List<String>> {
        return localPetDetailDao.observePetKindList().map { petKindList ->
            petKindList.map {
                it.name
            }
        }
    }
}