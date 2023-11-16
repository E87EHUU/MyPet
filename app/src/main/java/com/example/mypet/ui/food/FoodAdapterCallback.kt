package com.example.mypet.ui.food

import com.example.mypet.domain.food.FoodModel

interface FoodAdapterCallback {
    fun onItemClick(foodModel: FoodModel)
    fun onSwitchActive(foodModel: FoodModel)
}