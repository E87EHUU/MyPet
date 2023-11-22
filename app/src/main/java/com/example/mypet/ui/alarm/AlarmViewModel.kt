package com.example.mypet.ui.alarm

import android.media.RingtoneManager
import androidx.lifecycle.ViewModel
import com.example.mypet.domain.alarm.AlarmModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(

) : ViewModel() {
    var ringtonePath: String? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).path
    var isVibration: Boolean = true
    var isDelay: Boolean = true
    val alarmModel
        get() = AlarmModel(ringtonePath, isVibration, isDelay)

    fun update(alarmFragmentArgs: AlarmFragmentArgs) {
        ringtonePath = alarmFragmentArgs.alarmModel.ringtonePath
        isVibration = alarmFragmentArgs.alarmModel.isVibration
        isDelay = alarmFragmentArgs.alarmModel.isDelay
    }
}