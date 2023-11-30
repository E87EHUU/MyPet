package com.example.mypet.domain.care

const val FOOD_KEY = "food"
const val START_KEY = "start"
const val REPEAT_KEY = "repeat"
const val END_KEY = "end"
const val ALARM_HEADER_KEY = "alarm-header"
const val ALARM_KEY = "alarm"

sealed class CareViewHolderModel(open val key: String)

data class CareViewHolderFoodModel(
    val text: String,
) : CareViewHolderModel(FOOD_KEY)

data class CareViewHolderStartModel(
    val description: String,
) : CareViewHolderModel(START_KEY)

data class CareViewHolderRepeatModel(
    val description: String,
) : CareViewHolderModel(REPEAT_KEY)

data class CareViewHolderHeaderAlarmModel(
    val description: String,
) : CareViewHolderModel(ALARM_HEADER_KEY)

data class CareViewHolderEndModel(
    val description: String,
) : CareViewHolderModel(END_KEY)

data class CareViewHolderAlarmModel(
    val id: Int,
    val time: String,
    val isActive: Boolean
) : CareViewHolderModel("$ALARM_KEY-$id")