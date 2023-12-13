package com.example.mypet.domain.care


const val CARE_ADAPTER_MAIN_KEY = "main"
const val CARE_ADAPTER_START_KEY = "start"
const val CARE_ADAPTER_REPEAT_KEY = "repeat"
const val CARE_ADAPTER_ALARM_KEY = "alarm"

sealed class CareModel(val key: String)