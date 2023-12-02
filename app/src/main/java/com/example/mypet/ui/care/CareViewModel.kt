package com.example.mypet.ui.care

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.CareRepository
import com.example.mypet.domain.alarm.AlarmMinModel
import com.example.mypet.domain.care.CareAlarmModel
import com.example.mypet.domain.care.CareMainModel
import com.example.mypet.domain.care.CareRepeatModel
import com.example.mypet.domain.care.CareStartModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CareViewModel @Inject constructor(
    private val careRepository: CareRepository
) : ViewModel() {
    private val _careMainModel = MutableStateFlow<CareMainModel?>(null)
    val careMainModel = _careMainModel.asStateFlow()

    private val _careStartModel = MutableStateFlow<CareStartModel?>(null)
    val careStartModel = _careStartModel.asStateFlow()

    private val _careRepeatModel = MutableStateFlow<CareRepeatModel?>(null)
    val careRepeatModel = _careRepeatModel.asStateFlow()

    private val _careAlarmModel = MutableStateFlow<CareAlarmModel?>(null)
    val careAlarmModel = _careAlarmModel.asStateFlow()

    fun updateCare(careId: Int, careTypeOrdinal: Int) {
        updateCareMain(careId, careTypeOrdinal)
        updateCareStart(careId, careTypeOrdinal)
        updateCareRepeat(careId, careTypeOrdinal)
        updateCareAlarm(careId, careTypeOrdinal)
    }

    private fun updateCareMain(careId: Int, careTypeOrdinal: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            careRepository.getCareMainModel(careId, careTypeOrdinal)
                .collectLatest {
                    _careMainModel.value = it
                }
        }

    private fun updateCareStart(careId: Int, careTypeOrdinal: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            careRepository.getCareStartModel(careId, careTypeOrdinal)
                .collectLatest {
                    _careStartModel.value = it
                }
        }

    private fun updateCareRepeat(careId: Int, careTypeOrdinal: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            careRepository.getCareRepeatModel(careId, careTypeOrdinal)
                .collectLatest {
                    _careRepeatModel.value = it
                }
        }

    private fun updateCareAlarm(careId: Int, careTypeOrdinal: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            careRepository.getCareAlarmModel(careId, careTypeOrdinal)
                .collectLatest {
                    _careAlarmModel.value = it
                }
        }

    fun switchAlarmState(alarmMinModel: AlarmMinModel) {
        /*        foodModel.toAlarmSwitchModel()?.let {
                    viewModelScope.launch(Dispatchers.IO) {
                        alarmRepository.switch(it)
                    }
                }*/
    }
}