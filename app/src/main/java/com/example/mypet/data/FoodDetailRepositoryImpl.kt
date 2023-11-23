package com.example.mypet.data

import android.net.Uri
import com.example.mypet.data.local.room.dao.LocalFoodDetailDao
import com.example.mypet.data.local.room.model.alarm.LocalFoodDetailModel
import com.example.mypet.domain.FoodDetailRepository
import com.example.mypet.domain.food.detail.FoodDetailModel
import javax.inject.Inject

class FoodDetailRepositoryImpl @Inject constructor(
    private val foodDetailDao: LocalFoodDetailDao,
) : FoodDetailRepository {
    override suspend fun getFoodDetailModel(foodId: Int) =
        foodDetailDao.getLocalFoodDetailModel(foodId)?.toFoodDetailModel()

    override suspend fun saveAndSetFoodDetailModel(foodDetailModel: FoodDetailModel) {
        TODO("Not yet implemented")
    }

    private fun LocalFoodDetailModel.toFoodDetailModel() =
        FoodDetailModel(
            foodId,
            foodTitle,
            foodRation,
            myAvatarUri = myAvatarPath?.let { Uri.parse(it) },
            kindOrdinal,
            breedOrdinal,
            alarmId,
            alarmHour,
            alarmMinute,
            alarmIsRepeatMonday,
            alarmIsRepeatTuesday,
            alarmIsRepeatWednesday,
            alarmIsRepeatThursday,
            alarmIsRepeatFriday,
            alarmIsRepeatSaturday,
            alarmIsRepeatSunday,
            alarmRingtonePath,
            alarmIsVibration,
            alarmIsDelay,
            alarmIsActive
        )
}