package com.example.mypet.ui.food.detail.alarm

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
class FoodDetailAlarmViewModel @Inject constructor(
    private val foodDetailAlarmRepository: FoodAlarmRepository,
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

    fun update(petDetailAlarmSetFragmentArgs: FoodDetailAlarmFragmentArgs): Flow<Unit>? {
        if (petDetailAlarmSetFragmentArgs.petMyId.isBadLocalId()) return null
        petMyId = petDetailAlarmSetFragmentArgs.petMyId

        if (petDetailAlarmSetFragmentArgs.petFoodId.isBadLocalId()) return null
        foodId = petDetailAlarmSetFragmentArgs.petFoodId

        return flow {
            foodId?.let { foodId ->
                foodDetailAlarmRepository.getFoodAlarmModel(foodId)
                    ?.let { foodDetailAlarmModel ->
                        name = foodDetailAlarmModel.title
                        if (foodDetailAlarmModel.alarmId > 0) {
                            alarmId = foodDetailAlarmModel.alarmId
                            hour = foodDetailAlarmModel.hour
                            minute = foodDetailAlarmModel.minute
                            isMondayChecked = foodDetailAlarmModel.isRepeatMonday
                            isTuesdayChecked = foodDetailAlarmModel.isRepeatTuesday
                            isWednesdayChecked = foodDetailAlarmModel.isRepeatWednesday
                            isThursdayChecked = foodDetailAlarmModel.isRepeatThursday
                            isFridayChecked = foodDetailAlarmModel.isRepeatFriday
                            isSaturdayChecked = foodDetailAlarmModel.isRepeatSaturday
                            isSundayChecked = foodDetailAlarmModel.isRepeatSunday
                            ringtonePath = foodDetailAlarmModel.ringtonePath
                            isVibrationChecked = foodDetailAlarmModel.isVibration
                            isDelayChecked = foodDetailAlarmModel.isDelay
                            isActive = foodDetailAlarmModel.isActive
                        }

                        emit(Unit)
                    }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun save() =
        flow {
            val saveAlarmModel = SaveAndSetFoodAlarmModel(
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

            foodDetailAlarmRepository.saveAndSetFoodDetailAlarm(saveAlarmModel)

            emit(Unit)
        }.flowOn(Dispatchers.IO)
}