package com.example.mypet.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri

class RingtonePlayer(
    private val context: Context,
    private val ringtoneUri: Uri,
) {
    private var ringtone: Ringtone? = null
    fun play() {
        if (ringtone != null) stop()

        val ringtoneAttributes =
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()

        ringtone = RingtoneManager.getRingtone(context, ringtoneUri)
        ringtone?.audioAttributes = ringtoneAttributes
        ringtone?.play()
    }

    fun stop() {
        ringtone?.stop()
        ringtone = null
    }
}