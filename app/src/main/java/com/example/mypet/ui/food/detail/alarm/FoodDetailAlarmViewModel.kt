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

    private var localDateTime: LocalDateTime = LocalDateTime.now()

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

    var ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

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
                            ringtoneUri = foodDetailAlarmModel.ringtoneUri
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
                alarmMelodyURI = ringtoneUri,
                alarmIsVibration = isVibrationChecked,
                alarmIsDelay = isDelayChecked,
            )

            foodDetailAlarmRepository.saveAndSetFoodDetailAlarm(saveAlarmModel)

            emit(Unit)
        }.flowOn(Dispatchers.IO)
}