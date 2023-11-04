package com.example.mypet.ui.food.detail.alarm

import android.media.RingtoneManager
import androidx.lifecycle.ViewModel
import com.example.mypet.domain.FoodDetailAlarmRepository
import com.example.mypet.domain.food.detail.alarm.SaveFoodDetailAlarmAndSetAlarm
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
    private val foodDetailAlarmRepository: FoodDetailAlarmRepository,
) : ViewModel() {
    private var petMyId by Delegates.notNull<Int>()
    private var foodId: Int? = null
    private var alarmId: Int? = null

    private var localDateTime = LocalDateTime.now()

    var name = ""

    var hour = localDateTime.hour
    var minute = localDateTime.minute

    var isMondayChecked = false
    var isTuesdayChecked = false
    var isWednesdayChecked = false
    var isThursdayChecked = false
    var isFridayChecked = false
    var isSaturdayChecked = false
    var isSundayChecked = false

    var melodiURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

    var isVibrationChecked = false
    var isDelayChecked = false
    var isActive = true

    private fun Int?.isBadLocalId() =
        this == null || this == 0

    fun update(petDetailAlarmSetFragmentArgs: FoodDetailAlarmFragmentArgs): Flow<Unit>? {
        if (petDetailAlarmSetFragmentArgs.petMyId.isBadLocalId()) return null
        petMyId = petDetailAlarmSetFragmentArgs.petMyId

        if (petDetailAlarmSetFragmentArgs.petFoodId.isBadLocalId()) return null
        foodId = petDetailAlarmSetFragmentArgs.petFoodId

        return flow {
            foodId?.let { foodId ->
                foodDetailAlarmRepository.getFoodDetailAlarmModel(foodId)
                    ?.let { foodDetailAlarmModel ->
                        name = foodDetailAlarmModel.name
                        with(foodDetailAlarmModel) {
                            alarmId?.let { alarmId = it }
                            alarmHour?.let { hour = it }
                            alarmMinute?.let { minute = it }
                            alarmRepeatMonday?.let { isMondayChecked = it }
                            alarmRepeatTuesday?.let { isTuesdayChecked = it }
                            alarmRepeatWednesday?.let { isWednesdayChecked = it }
                            alarmRepeatThursday?.let { isThursdayChecked = it }
                            alarmRepeatFriday?.let { isFridayChecked = it }
                            alarmRepeatSaturday?.let { isSaturdayChecked = it }
                            alarmRepeatSunday?.let { isSundayChecked = it }
                            alarmMelodyURI?.let { melodiURI = it }
                            alarmIsVibration?.let { isVibrationChecked = it }
                            alarmIsDelay?.let { isDelayChecked = it }
                            alarmIsActive?.let { isActive = it }
                        }

                        emit(Unit)
                    }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun save() =
        flow {
            val saveAlarmModel = SaveFoodDetailAlarmAndSetAlarm(
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
                alarmMelodyURI = melodiURI,
                alarmIsVibration = isVibrationChecked,
                alarmIsDelay = isDelayChecked,
            )

            foodDetailAlarmRepository.saveAndSetFoodDetailAlarm(saveAlarmModel)

            emit(Unit)
        }.flowOn(Dispatchers.IO)
}