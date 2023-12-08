package com.example.mypet.ui.care.alarm.detail

import android.media.RingtoneManager
import androidx.lifecycle.ViewModel
import com.example.mypet.domain.alarm.AlarmModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CareAlarmDetailViewModel @Inject constructor(

) : ViewModel() {
    var alarmModel: AlarmModel? = null
        private set

    var ringtonePath: String? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).path
    var isVibration: Boolean = true
    var isDelay: Boolean = true

    fun update(alarmId: Int) {
/*        this.alarmModel = alarmModel

        ringtonePath = alarmModel.ringtonePath
        alarmModel.isVibration?.let { isVibration = it }
        alarmModel.isDelay?.let { isDelay = it }*/
    }
}