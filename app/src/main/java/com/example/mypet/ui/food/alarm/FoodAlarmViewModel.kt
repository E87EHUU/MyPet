package com.example.mypet.ui.food.alarm

import android.media.RingtoneManager
import androidx.lifecycle.ViewModel
import com.example.mypet.domain.FoodAlarmRepository
import com.example.mypet.domain.food.SaveAndSetFoodAlarmModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class FoodAlarmViewModel @Inject constructor(
    private val foodAlarmRepository: FoodAlarmRepository,
) : ViewModel() {
    private var petMyId by Delegates.notNull<Int>()
    private var foodId: Int? = null
    private var alarmId: Int? = null

    private var localDateTime: LocalDateTime = LocalDateTime.now()

    var name = ""

    var hour = localDateTime.hour
    var minute = localDateTime.minute

    var isMondayChecked = true
    var isTuesdayChecked = true
    var isWednesdayChecked = true
    var isThursdayChecked = true
    var isFridayChecked = true
    var isSaturdayChecked = true
    var isSundayChecked = true

    var ringtonePath: String? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).path
    var isVibrationChecked = true
    var isDelayChecked = true
    private var isActive = true

    private fun Int?.isBadLocalId() =
        this == null || this == 0

    fun update(foodAlarmFragmentArgs: FoodAlarmFragmentArgs): Flow<Unit>? {
        if (foodAlarmFragmentArgs.petMyId.isBadLocalId()) return null
        petMyId = foodAlarmFragmentArgs.petMyId

        if (foodAlarmFragmentArgs.petFoodId.isBadLocalId()) return null
        foodId = foodAlarmFragmentArgs.petFoodId

        return flow {
            foodId?.let { foodId ->
                foodAlarmRepository.getFoodAlarmModel(foodId)
                    ?.let { foodAlarmModel ->
                        name = foodAlarmModel.foodTitle
                        if (foodAlarmModel.alarmId > 0) {
                            alarmId = foodAlarmModel.alarmId
                            hour = foodAlarmModel.hour
                            minute = foodAlarmModel.minute
                            isMondayChecked = foodAlarmModel.isRepeatMonday
                            isTuesdayChecked = foodAlarmModel.isRepeatTuesday
                            isWednesdayChecked = foodAlarmModel.isRepeatWednesday
                            isThursdayChecked = foodAlarmModel.isRepeatThursday
                            isFridayChecked = foodAlarmModel.isRepeatFriday
                            isSaturdayChecked = foodAlarmModel.isRepeatSaturday
                            isSundayChecked = foodAlarmModel.isRepeatSunday
                            ringtonePath = foodAlarmModel.ringtonePath
                            isVibrationChecked = foodAlarmModel.isVibration
                            isDelayChecked = foodAlarmModel.isDelay
                            isActive = foodAlarmModel.isActive
                        }

                        emit(Unit)
                    }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun save() =
        flow {
            val saveAndSetFoodAlarmModel = SaveAndSetFoodAlarmModel(
                petMyId = petMyId,

                foodId = foodId,
                foodName = name.ifEmpty { "" },

                alarmId = alarmId,
                alarmHour = hour,
                alarmMinute = minute,
                alarmRepeatMonday = isMondayChecked,
                alarmRepeatTuesday = isTuesdayChecked,
                alarmRepeatWednesday = isWednesdayChecked,
                alarmRepeatThursday = isThursdayChecked,
                alarmRepeatFriday = isFridayChecked,
                alarmRepeatSaturday = isSaturdayChecked,
                alarmRepeatSunday = isSundayChecked,
                alarmRingtonePath = ringtonePath,
                alarmIsVibration = isVibrationChecked,
                alarmIsDelay = isDelayChecked,
            )

            foodAlarmRepository.saveAndSetFoodDetailAlarm(saveAndSetFoodAlarmModel)

            emit(Unit)
        }.flowOn(Dispatchers.IO)
}