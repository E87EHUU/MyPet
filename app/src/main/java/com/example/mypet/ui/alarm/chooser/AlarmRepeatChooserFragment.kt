package com.example.mypet.ui.alarm.chooser

import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import com.example.mypet.app.R
import com.example.mypet.ui.alarm.AlarmViewModel


class AlarmRepeatChooserFragment : DialogFragment(R.layout.fragment_alarm_repeat_chooser) {
    private val viewModel by navGraphViewModels<AlarmViewModel>(R.id.navigationAlarm) { defaultViewModelProviderFactory }


}