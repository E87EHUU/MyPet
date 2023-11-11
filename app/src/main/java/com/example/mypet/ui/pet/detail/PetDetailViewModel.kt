package com.example.mypet.ui.pet.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.data.alarm.AlarmDao
import com.example.mypet.data.alarm.AlarmModel
import com.example.mypet.domain.PetDetailRepository
import com.example.mypet.domain.pet.detail.PetFoodModel
import com.example.mypet.domain.pet.detail.PetModel
import com.example.mypet.domain.pet.detail.SwitchPetFoodAlarmStateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetDetailViewModel @Inject constructor(
    private val petDetailRepository: PetDetailRepository,
    private val alarmDao: AlarmDao
) : ViewModel() {
    private val _petModel = MutableStateFlow<PetModel?>(null)
    val petModel = _petModel.asStateFlow()

    fun updatePetModel() = viewModelScope.launch(Dispatchers.IO) {
        petDetailRepository.observePetDetail()
            .collectLatest { _petModel.value = it }
    }

    fun switchPetFoodAlarmState(petFoodModel: PetFoodModel) {
        petFoodModel.alarmId ?: return
        petFoodModel.alarmIsActive ?: return

        viewModelScope.launch(Dispatchers.IO) {
            val switchPetFoodAlarmStateModel =
                SwitchPetFoodAlarmStateModel(
                    alarmId = petFoodModel.alarmId,
                    alertIsActive = !petFoodModel.alarmIsActive
                )
            petDetailRepository.switchPetFoodAlarmState(switchPetFoodAlarmStateModel)
        }
    }

    fun setAlarm() = viewModelScope.launch(Dispatchers.IO) {
        val alarmModel = AlarmModel(1, 1, 1)
        alarmDao.setAlarm(alarmModel)
    }

    val petMyId
        get() = petModel.value?.id ?: TODO("Не удалось получить id питомца")
}