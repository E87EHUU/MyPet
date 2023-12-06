package com.example.mypet.domain.care

import com.example.mypet.app.R

enum class CareTypes(val titleResId: Int, val iconResId: Int) {
    FOOD(R.string.care_food_title, R.drawable.feed),
    BATH(R.string.care_bath_title, R.drawable.ic_care_bath),
    COMBING_THE_WOOL(R.string.care_combing_the_wool_title, R.drawable.ic_care_brushing),
    AGAINST_FLEAS_WORMS(
        R.string.care_against_worms_title,
        R.drawable.ic_care_against_worms
    ),
    AGAINST_FLEAS_AND_TICKS(
        R.string.care_against_fleas_and_ticks_title,
        R.drawable.ic_care_against_fleas_and_ticks
    ),
    WALK(R.string.care_walk_title, R.drawable.home)
}