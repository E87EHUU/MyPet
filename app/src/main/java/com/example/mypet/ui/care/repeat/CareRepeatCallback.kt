package com.example.mypet.ui.care.repeat

interface CareRepeatCallback {
    fun onClickRepeat()
    fun onClickReset()
    fun generateDayAlarm(dayTimes: Int)
}