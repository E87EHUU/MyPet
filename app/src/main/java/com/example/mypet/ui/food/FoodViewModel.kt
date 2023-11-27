package com.example.mypet.ui.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.AlarmRepository
import com.example.mypet.domain.FoodRepository
import com.example.mypet.domain.food.CareAlarmHeaderModel
import com.example.mypet.domain.food.CareEndModel
import com.example.mypet.domain.food.CareFoodModel
import com.example.mypet.domain.food.CareModel
import com.example.mypet.domain.food.CareRepeatModel
import com.example.mypet.domain.food.CareStartModel
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
    private val _food = MutableStateFlow<List<CareModel>>(emptyList())
    val food = _food.asStateFlow()

    var petMyId: Int? = null

    fun updateFood(petMyId: Int) {
        this.petMyId = petMyId

        viewModelScope.launch(Dispatchers.IO) {
            foodRepository.getFoodModels(petMyId)
                .collectLatest {
                    val mutableFoodModels = mutableListOf(
                        CareFoodModel(""),
                        CareStartModel(""),
                        CareRepeatModel(""),
                        CareEndModel("")
                    )
                    if (it.isNotEmpty()) {
                        mutableFoodModels.add(CareAlarmHeaderModel(""))
                        mutableFoodModels.addAll(it)
                    }
                    _food.value = mutableFoodModels
                }
        }
    }

    fun switchAlarmState(foodModel: CareModel) {
        /*        foodModel.toAlarmSwitchModel()?.let {
                    viewModelScope.launch(Dispatchers.IO) {
                        alarmRepository.switch(it)
                    }
                }*/
    }
}