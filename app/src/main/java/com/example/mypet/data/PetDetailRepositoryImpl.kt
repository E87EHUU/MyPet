package com.example.mypet.data

import android.net.Uri
import kotlinx.coroutines.flow.map
import com.example.mypet.data.local.room.dao.LocalPetDetailDao
import com.example.mypet.data.local.room.model.LocalPetModel
import com.example.mypet.domain.PetDetailRepository
import com.example.mypet.domain.pet.detail.PetModel
import javax.inject.Inject


class PetDetailRepositoryImpl @Inject constructor(
    private val localPetDetailDao: LocalPetDetailDao,
    private val alarmDao: AlarmDao,
) : PetDetailRepository {
    override fun observePetDetail() =
        localPetDetailDao.observeActivePet()
            .mapNotNull { localPetModels ->
                val petFoods = mutableListOf<PetFoodModel>()
                localPetModels.forEach {
                    petFoods.add(it.toPetFoodModel())
                }
                localPetModels.toPetModel(petFoods)
            }

    override suspend fun switchPetFoodAlarmState(switchPetFoodAlarmStateModel: SwitchPetFoodAlarmStateModel) {
        with(switchPetFoodAlarmStateModel) {
            if (alertIsActive) {
                localPetDetailDao
                    .getLocalFoodDetailAlarmModelByAlertId(alarmId)
                    ?.toAlarmModel()
                    ?.let {
                        alarmDao.setAlarm(it)
                        localPetDetailDao.switchPetFoodAlarmState(alarmId, true)
                        return
                    }
            }

    override fun observePetListDetail(): Flow<List<PetModel?>> {
        return localPetDetailDao.observePetList().map { petList ->
            petList.map {
                it?.toPetModel()
            }
        }
    }

    private fun LocalPetModel.toPetModel() =
        PetModel(
            id = id,
            avatar = Uri.parse(avatar),
            name = name,
            age = age,
            weight = weight,
            breedName = breedName
            alarmDao.removeAlarm(alarmId)
            localPetDetailDao.switchPetFoodAlarmState(alarmId, false)
        }
    }

    private fun List<LocalPetModel>.toPetModel(petFoodModels: MutableList<PetFoodModel>) =
        first().run {
            PetModel(
                id = id,
                avatar = Uri.parse(avatar),
                name = name,
                age = age,
                weight = weight,
                breedName = breedName,
                foods = petFoodModels.toList()
            )
        }

    private fun LocalPetModel.toPetFoodModel() =
        PetFoodModel(
            id = foodId,
            name = foodName,
            alarmId = alarmId,
            alarmHour = alarmHour,
            alarmMinute = alarmMinute,
            alarmIsActive = alarmIsActive
        )

    private fun LocalFoodDetailAlarmModel.toAlarmModel() =
        AlarmModel(alarmId, hour, minute)
}