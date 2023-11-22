package com.example.mypet.ui.food.detail

import android.media.RingtoneManager
import androidx.lifecycle.ViewModel
import com.example.mypet.domain.FoodDetailRepository
import com.example.mypet.domain.food.detail.FoodDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val foodDetailRepository: FoodDetailRepository,
) : ViewModel() {
    private var foodDetailModel: FoodDetailModel? = null

    private var localDateTime: LocalDateTime = LocalDateTime.now()

    var title = ""

    var hour = localDateTime.hour
    var minute = localDateTime.minute

    var isRepeatMonday = true
    var isRepeatTuesday = true
    var isRepeatWednesday = true
    var isRepeatThursday = true
    var isRepeatFriday = true
    var isRepeatSaturday = true
    var isRepeatSunday = true

    var ringtonePath: String? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).path
    var isVibration = true
    var isDelay = true
    private var isActive = true

    fun update(petFoodId: Int): Flow<Unit>? {
        if (petFoodId == 0) return null

        return flow {
            foodDetailRepository.getFoodDetailModel(petFoodId)
                ?.let { itFoodDetailModel ->
                    foodDetailModel = itFoodDetailModel
                    title = itFoodDetailModel.foodTitle
                    itFoodDetailModel.alarmHour?.let { hour = it }
                    itFoodDetailModel.alarmMinute?.let { minute = it }
                    itFoodDetailModel.alarmIsRepeatMonday?.let { isRepeatMonday = it }
                    itFoodDetailModel.alarmIsRepeatTuesday?.let { isRepeatTuesday = it }
                    itFoodDetailModel.alarmIsRepeatWednesday?.let { isRepeatWednesday = it }
                    itFoodDetailModel.alarmIsRepeatThursday?.let { isRepeatThursday = it }
                    itFoodDetailModel.alarmIsRepeatFriday?.let { isRepeatFriday = it }
                    itFoodDetailModel.alarmIsRepeatSaturday?.let { isRepeatSaturday = it }
                    itFoodDetailModel.alarmIsRepeatSunday?.let { isRepeatSunday = it }
                    itFoodDetailModel.alarmRingtonePath?.let { ringtonePath = it }
                    itFoodDetailModel.alarmIsVibration?.let { isVibration = it }
                    itFoodDetailModel.alarmIsDelay?.let { isDelay = it }
                    itFoodDetailModel.alarmIsActive?.let { isActive = it }

                    emit(Unit)
                }
        }.flowOn(Dispatchers.IO)
    }

    fun save() = flow {
        foodDetailModel?.let {
            val copyFoodDetailModel =
                it.copy(
                    foodTitle = title,
                    alarmHour = hour,
                    alarmMinute = minute,
                    alarmIsRepeatMonday = isRepeatMonday,
                    alarmIsRepeatTuesday = isRepeatTuesday,
                    alarmIsRepeatWednesday = isRepeatWednesday,
                    alarmIsRepeatThursday = isRepeatThursday,
                    alarmIsRepeatFriday = isRepeatFriday,
                    alarmIsRepeatSaturday = isRepeatSaturday,
                    alarmIsRepeatSunday = isRepeatSunday,
                    alarmRingtonePath = ringtonePath,
                    alarmIsVibration = isVibration,
                    alarmIsDelay = isDelay,
                )
            foodDetailRepository.saveAndSetFoodDetailModel(copyFoodDetailModel)
            emit(Unit)
        }
    }.flowOn(Dispatchers.IO)
}