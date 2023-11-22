package com.example.mypet.ui.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.AlarmRepository
import com.example.mypet.domain.FoodRepository
import com.example.mypet.domain.food.FoodModel
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
    private val alarmRepository: AlarmRepository,
) : ViewModel() {
    private val _food = MutableStateFlow<List<FoodModel>>(emptyList())
    val food = _food.asStateFlow()

    var petMyId: Int? = null

    fun updateFood(petMyId: Int) {
        this.petMyId = petMyId

        viewModelScope.launch(Dispatchers.IO) {
            foodRepository.observeFoodModels(petMyId)
                .collectLatest { _food.value = it }
        }
    }

    fun switchAlarmState(foodModel: FoodModel) {
        foodModel.toAlarmSwitchModel()?.let {
            viewModelScope.launch(Dispatchers.IO) {
                alarmRepository.switch(it)
            }
        }
    }
}