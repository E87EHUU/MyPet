package com.example.mypet.ui.care

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.CareRepository
import com.example.mypet.domain.alarm.AlarmMinModel
import com.example.mypet.domain.care.CareAdapterModel
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

    var hour: Int? = null
    var minute: Int? = null
    var date: Long? = null

    fun updateCare(careId: Int, careTypeOrdinal: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                careRepository.getCareMainModel(careId, careTypeOrdinal),
                careRepository.getCareStartModel(careId, careTypeOrdinal),
                careRepository.getCareRepeatModel(careId, careTypeOrdinal),
                careRepository.getCareAlarmModel(careId, careTypeOrdinal),
            ) { careMainModel, careStartModel, careRepeatModel, careAlarmModel ->
                val mutableCareAdapterModels = mutableListOf<CareAdapterModel>()

                mutableCareAdapterModels.add(careMainModel)
                careStartModel?.let { mutableCareAdapterModels.add(it) }
                careRepeatModel?.let { mutableCareAdapterModels.add(it) }
                mutableCareAdapterModels.add(careAlarmModel)

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