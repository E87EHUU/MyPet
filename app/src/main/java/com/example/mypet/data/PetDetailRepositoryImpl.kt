package com.example.mypet.data

import android.net.Uri
import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.alarm.AlarmModel
import com.example.mypet.data.local.room.dao.LocalPetDetailDao
import com.example.mypet.data.local.room.model.pet.LocalFoodDetailAlarmModel
import com.example.mypet.data.local.room.model.pet.LocalPetModel
import com.example.mypet.domain.PetDetailRepository
import com.example.mypet.domain.pet.detail.PetFoodModel
import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.domain.pet.detail.SwitchPetFoodAlarmStateModel
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject


class PetDetailRepositoryImpl @Inject constructor(
    private val localPetDetailDao: LocalPetDetailDao,
    private val alarmDao: AlarmDao,
) : PetDetailRepository {
    override fun observePetListDetail() =
        localPetDetailDao.observePetList()
            .mapNotNull { localPetModels ->
                val pets = mutableListOf<PetModel>()
                val petFoods = mutableListOf<PetFoodModel>()

                var petId = localPetModels.first().id

                localPetModels.forEachIndexed { index, localPetModel ->
                    if (localPetModel.id != petId) {
                        pets.add(localPetModels.toPetModel(index, petFoods))
                        petFoods.clear()
                        petId = localPetModel.id
                    }

                    petFoods.add(localPetModel.toPetFoodModel())
                }

                pets.add(localPetModels.toPetModel(localPetModels.lastIndex, petFoods))

                pets.toList()
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

            alarmDao.removeAlarm(alarmId)
            localPetDetailDao.switchPetFoodAlarmState(alarmId, false)
        }
    }


    private fun List<LocalPetModel>.toPetModel(
        index: Int,
        petFoodModels: MutableList<PetFoodModel>
    ) =
        this[index].run {
            PetModel(
                id = id,
                avatarUri = Uri.parse(avatar),
                name = name,
                age = age,
                weight = weight,
                breedName = breedName,
                isActive = isActive,
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