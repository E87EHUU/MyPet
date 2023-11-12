package com.example.mypet.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mypet.domain.BootCompleteRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedReceiver : BroadcastReceiver() {
    @Inject
    lateinit var bootCompleteRepository: BootCompleteRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            bootCompleteRepository.setAllAlarm()
        }
    }
}