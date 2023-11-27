package com.example.mypet.ui.food.detail

import androidx.lifecycle.ViewModel
import com.example.mypet.domain.FoodDetailRepository
import com.example.mypet.domain.alarm.AlarmAlertType
import com.example.mypet.domain.alarm.AlarmModel
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
    var ration = ""

    var hour = localDateTime.hour
    var minute = localDateTime.minute

    var alarmModel: AlarmModel? = null
        get() {
            return if (field == null) foodDetailModel?.toAlarmModel() ?: AlarmModel()
            else field
        }

    var alarmAlertTypeOrdinal = AlarmAlertType.ONLY_NOTIFICATION.ordinal

    private var isActive = true

    fun update(petFoodId: Int): Flow<Unit>? {
        if (petFoodId == 0) return null

        return flow {
            foodDetailRepository.getFoodDetailModel(petFoodId)
                ?.let { itFoodDetailModel ->
                    foodDetailModel = itFoodDetailModel

                    with(itFoodDetailModel) {
                        foodTitle?.let { title = it }
                        foodRation?.let { ration = it }
                        alarmHour?.let { hour = it }
                        alarmMinute?.let { minute = it }
                        alarmIsActive?.let { isActive = it }
                    }

                    emit(Unit)
                }
        }.flowOn(Dispatchers.IO)
    }

    fun save() = flow {
        foodDetailModel?.let {
            val saveFoodDetailModel = it.copy(
                foodTitle = title,
                foodRation = ration,
                alarmHour = hour,
                alarmMinute = minute,
            )

            foodDetailRepository.saveAndSetFoodDetailModel(saveFoodDetailModel)
            emit(Unit)
        }
    }.flowOn(Dispatchers.IO)
}