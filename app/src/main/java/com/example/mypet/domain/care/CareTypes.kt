package com.example.mypet.domain.care

import com.example.mypet.app.R

enum class CareTypes(val titleStringRes: Int, val iconResId: Int) {
    FOOD(R.string.food_title, R.drawable.icon_point),
    BATH(R.string.bath_title, R.drawable.icon_point),
    COMBING_THE_WOOL(R.string.combing_the_wall_title, R.drawable.icon_point),
    VACCINATION(R.string.vaccination_title, R.drawable.icon_point),
    WALK(R.string.walk_title, R.drawable.icon_point)
}