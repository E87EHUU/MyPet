package com.example.mypet.ui.care

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.CareRepository
import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.CareEndModel
import com.example.mypet.domain.care.CareMainModel
import com.example.mypet.domain.care.CareModel
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.CareStartModel
import com.example.mypet.domain.care.alarm.CareAlarmDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CareViewModel @Inject constructor(
    private val careRepository: CareRepository
) : ViewModel() {
    private val _careModels = MutableStateFlow<MutableList<CareModel>>(mutableListOf())
    val careModels = _careModels.asStateFlow()

    var careMainModel: CareMainModel? = null
    var careStartModel: CareStartModel? = null
    var careRepeatModel: CareRepeatModel? = null
    var careEndModel: CareEndModel? = null
    var careAlarmModel: CareAlarmModel? = null
    var careAlarmDetailMainModel: CareAlarmDetailModel? = null

    fun updateCare(petId: Int, careId: Int, careTypeOrdinal: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                careRepository.getCareMainModel(petId, careTypeOrdinal),
                careRepository.getCareStartModel(careId, careTypeOrdinal),
                careRepository.getCareRepeatModel(careId, careTypeOrdinal),
                careRepository.getCareAlarmModel(careId, careTypeOrdinal),
            ) { mainModel, startModel, repeatModel, alarmModel ->
                val mutableCareAdapterModels = mutableListOf<CareModel>()

                mutableCareAdapterModels.add(mainModel)
                careMainModel = mainModel
                startModel?.let {
                    careStartModel = it
                    mutableCareAdapterModels.add(it)
                }
                repeatModel?.let {
                    careRepeatModel = it
                    mutableCareAdapterModels.add(it)
                }
                careAlarmModel = alarmModel
                mutableCareAdapterModels.add(alarmModel)

                _careModels.value = mutableCareAdapterModels
            }.collect()
        }

    fun saveCare() = viewModelScope.launch(Dispatchers.IO) {
        careRepository.saveCareModels(_careModels.value)
            .collect()
    }

    fun alarmDelete(careAlarmDetailMainModel: CareAlarmDetailModel) {
        careAlarmModel?.let { careAlarmModel ->
            careAlarmModel.deletedAlarmIds.add(careAlarmDetailMainModel.id)

            val mutableList = careAlarmModel.alarms.toMutableList()
            mutableList.remove(careAlarmDetailMainModel)

            careAlarmModel.alarms = mutableList
        }
    }

    fun saveAlarm(careAlarmDetailMainModel: CareAlarmDetailModel, hour: Int, minute: Int) {
        careAlarmModel?.let { careAlarmModel ->
            val savable = careAlarmDetailMainModel.copy(hour = hour, minute = minute)

            val mutableList = careAlarmModel.alarms.toMutableList()
            mutableList.remove(careAlarmDetailMainModel)
            mutableList.add(savable)
            careAlarmModel.alarms = mutableList
        }
    }

    /*    private val list = listOf(
            8,
            20,
            12,
            16,
            22
        )

        fun generateDayAlarm(dayTimes: Int) {
            careAlarmModel?.let { careAlarmModel ->
                if (careAlarmModel.alarms.isEmpty()) {
                    careAlarmModel.alarms =
                        when (dayTimes) {
                            1 -> listOf(
                                CareAlarmDetailMainModel(hour = list[0])
                            )

                            2 -> listOf(
                                CareAlarmDetailMainModel(hour = list[0]),
                                CareAlarmDetailMainModel(hour = list[1])
                            )

                            3 -> listOf(
                                CareAlarmDetailMainModel(hour = list[0]),
                                CareAlarmDetailMainModel(hour = list[1]),
                                CareAlarmDetailMainModel(hour = list[2]),
                            )

                            4 -> listOf(
                                CareAlarmDetailMainModel(hour = list[0]),
                                CareAlarmDetailMainModel(hour = list[1]),
                                CareAlarmDetailMainModel(hour = list[2]),
                                CareAlarmDetailMainModel(hour = list[3]),
                            )

                            5 -> listOf(
                                CareAlarmDetailMainModel(hour = list[0]),
                                CareAlarmDetailMainModel(hour = list[1]),
                                CareAlarmDetailMainModel(hour = list[2]),
                                CareAlarmDetailMainModel(hour = list[3]),
                                CareAlarmDetailMainModel(hour = list[4]),
                            )

                            else -> emptyList()
                        }
                }
            }
        }*/
}