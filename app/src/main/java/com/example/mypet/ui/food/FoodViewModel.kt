package com.example.mypet.ui.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.FoodRepository
import com.example.mypet.domain.food.FoodModel
import com.example.mypet.domain.food.SwitchFoodAlarmModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
) : ViewModel() {
    private val _food = MutableStateFlow<List<FoodModel>>(emptyList())
    val food = _food.asStateFlow()

    fun updateFood(activePetId: Int?) = viewModelScope.launch(Dispatchers.IO) {
        foodRepository.observeFoodModels(activePetId)
            .collectLatest { _food.value = it }
    }

    fun switchAlarmState(foodModel: FoodModel) {
        foodModel.alarmId ?: return
        foodModel.alarmIsActive ?: return

        viewModelScope.launch(Dispatchers.IO) {
            val switchFoodAlarmModel =
                SwitchFoodAlarmModel(
                    alarmId = foodModel.alarmId,
                    alertIsActive = !foodModel.alarmIsActive
                )
            foodRepository.switchFoodAlarm(switchFoodAlarmModel)
        }
    }
}