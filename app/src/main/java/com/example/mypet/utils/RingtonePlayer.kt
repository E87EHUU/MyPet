package com.example.mypet.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri

class RingtonePlayer(
    private val context: Context,
) {
    private var ringtone: Ringtone? = null
    private val audioAttributes =
        AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()

    fun play(ringtoneUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)) {
        if (ringtone == null) {
            ringtone = RingtoneManager.getRingtone(context, ringtoneUri)
            ringtone?.audioAttributes = audioAttributes
        }

        ringtone?.apply {
            if (!isPlaying) play()
        }
    }

    fun stop() {
        ringtone?.stop()
    }

    fun onDestroy() {
        ringtone = null
    }
}