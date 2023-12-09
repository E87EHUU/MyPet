package com.example.mypet.ui.care

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.CareRepository
import com.example.mypet.domain.alarm.AlarmMinModel
import com.example.mypet.domain.care.CareAdapterModel
import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.CareMainModel
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
    private val _careAdapterModels =
        MutableStateFlow<MutableList<CareAdapterModel>>(mutableListOf())
    val careAdapterModels = _careAdapterModels.asStateFlow()

    var careMainModel: CareMainModel? = null
    var careStartModel: CareStartModel? = null
    var careRepeatModel: CareRepeatModel? = null
    var careAlarmModel: CareAlarmModel? = null
    var careAlarmDetailModel: CareAlarmDetailModel? = null

    fun updateCare(careId: Int, careTypeOrdinal: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                careRepository.getCareMainModel(careId, careTypeOrdinal),
                careRepository.getCareStartModel(careId, careTypeOrdinal),
                careRepository.getCareRepeatModel(careId, careTypeOrdinal),
                careRepository.getCareAlarmModel(careId, careTypeOrdinal),
            ) { mainModel, startModel, repeatModel, alarmModel ->
                val mutableCareAdapterModels = mutableListOf<CareAdapterModel>()

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

                _careAdapterModels.value = mutableCareAdapterModels
            }.collect()
        }

    fun switchAlarmState(alarmMinModel: AlarmMinModel) {
        /*        foodModel.toAlarmSwitchModel()?.let {
                    viewModelScope.launch(Dispatchers.IO) {
                        alarmRepository.switch(it)
                    }
                }*/
    }
}