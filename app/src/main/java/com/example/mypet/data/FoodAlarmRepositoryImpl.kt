package com.example.mypet.data

/*
class FoodAlarmRepositoryImpl @Inject constructor(
    private val localFoodDetailAlarmDao: LocalFoodAlarmDao,
    private val alarmDao: IAlarmDao,
) : FoodAlarmRepository {
    override suspend fun getFoodAlarmModel(foodMyId: Int) =
        localFoodDetailAlarmDao.getLocalFoodAlarmModelByFoodId(foodMyId)
            ?.toFoodAlarmModel()

    override suspend fun saveAndSetFoodDetailAlarm(saveFoodDetailAlarmAndSetAlarm: SaveAndSetFoodDetailModel) {
        localFoodDetailAlarmDao.savePetFoodAndAlarm(saveFoodDetailAlarmAndSetAlarm)
            .toAlarmModel()?.let { alarmDao.setAlarm(it) }
    }

    private fun LocalFoodAlarmModel.toFoodAlarmModel() =
        FoodAlarmModel(
            foodId,
            foodTitle,
            null,
            petKindOrdinal,
            petBreedOrdinal,
            alarmId,
            hour,
            minute,
            isRepeatMonday,
            isRepeatTuesday,
            isRepeatWednesday,
            isRepeatThursday,
            isRepeatFriday,
            isRepeatSaturday,
            isRepeatSunday,
            ringtonePath,
            isVibration,
            isDelay,
            isActive
        )
}
*/
