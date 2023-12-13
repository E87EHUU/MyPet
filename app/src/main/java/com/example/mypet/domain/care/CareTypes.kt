package com.example.mypet.domain.care

import com.example.mypet.app.R

enum class CareTypes(val titleResId: Int, val iconResId: Int) {
    FOOD(R.string.care_food_title, R.drawable.feed),
    WALK(R.string.care_walk_title, R.drawable.home),
    BATH(R.string.care_bath_title, R.drawable.ic_care_bath),
    MEDICINE(R.string.care_medicine_title, R.drawable.home),
    GROOMING(R.string.care_grooming_title, R.drawable.home),
    TRAINING(R.string.care_training_title, R.drawable.home),
    VACCINATION(R.string.care_vaccination_title, R.drawable.home),
    VITAMIN(R.string.care_vitamin_title, R.drawable.home),
    CHECKUP(R.string.care_checkup_title, R.drawable.home),
    TOOTHBRUSHING(R.string.care_toothbrushing_title, R.drawable.home),
    COMBING_THE_WOOL(R.string.care_combing_the_wool_title, R.drawable.ic_care_brushing),
    AGAINST_FLEAS_WORMS(
        R.string.care_against_worms_title,
        R.drawable.ic_care_against_worms
    ),
    AGAINST_FLEAS_AND_TICKS(
        R.string.care_against_fleas_and_ticks_title,
        R.drawable.ic_care_against_fleas_and_ticks
    ),
}