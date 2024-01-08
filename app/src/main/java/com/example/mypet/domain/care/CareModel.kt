package com.example.mypet.domain.care


const val CARE_ADAPTER_MAIN_KEY = "main"
const val CARE_ADAPTER_START_KEY = "start"
const val CARE_ADAPTER_REPEAT_KEY = "repeat"
const val CARE_ADAPTER_END_KEY = "end"
const val CARE_ADAPTER_ALARM_KEY = "alarm"
const val CARE_ADAPTER_NOTE_KEY = "note"

sealed class CareModel(val key: String)