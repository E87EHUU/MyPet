package com.example.mypet.utils

import android.content.Context
import android.media.AudioAttributes
import android.os.VibrationEffect
import android.os.Vibrator

class VibrationPlayer(
    private val context: Context,
) {
    private var vibrator: Vibrator? = null
    private val audioAttributes =
        AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
    private val vibrationEffect: VibrationEffect =
        VibrationEffect.createWaveform(longArrayOf(1000, 300, 500), 1)

    fun play() {
        if (vibrator == null) {
            vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        vibrator?.apply {
            stop()
            if (hasVibrator()) vibrate(vibrationEffect, audioAttributes)
        }
    }

    fun stop() {
        vibrator?.cancel()
    }

    fun onDestroy() {
        vibrator = null
    }
}