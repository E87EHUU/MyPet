package com.example.mypet.ui.food.alarm.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.isVisible
import com.example.mypet.app.R
import com.example.mypet.domain.FoodAlarmServiceRepository
import com.example.mypet.domain.food.detail.alarm.FoodAlarmModel
import com.example.mypet.ui.MainActivity
import com.example.mypet.utils.RingtonePlayer
import com.example.mypet.utils.VibrationPlayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class FoodAlarmService : Service() {
    @Inject
    lateinit var foodAlarmServiceRepository: FoodAlarmServiceRepository

    private val windowManager by lazy { getSystemService(Context.WINDOW_SERVICE) as WindowManager }
    private var view: View? = null
    private var params: WindowManager.LayoutParams? = null

    private val buttonDelay
        get() = view?.findViewById<Button>(R.id.buttonAlarmDelay)
    private val buttonStop
        get() = view?.findViewById<Button>(R.id.buttonAlarmStop)

    private var foodAlarmModel: FoodAlarmModel? = null
    private lateinit var ownNotification: FoodAlarmServiceNotification
    private val ringtonePlayer: RingtonePlayer = RingtonePlayer(this)
    private val vibrationPlayer: VibrationPlayer = VibrationPlayer(this)

    override fun onBind(intent: Intent) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (intent.action) {
                ALARM_OVERLAY_ACTION_START -> start(intent)
                ALARM_OVERLAY_ACTION_STOP -> stop()
                ALARM_OVERLAY_ACTION_DELAY -> delay()
                ALARM_OVERLAY_ACTION_NAV_TO_DETAIL -> navToDetail()
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

        ringtonePlayer.onDestroy()
        vibrationPlayer.onDestroy()

        foodAlarmModel = null
        params = null
        view = null
    }

    private fun start(intent: Intent) {
        val alarmId = intent.getIntExtra(ALARM_ID, 0)
        if (alarmId < 0) return

        if (foodAlarmModel == null) {
            runBlocking {
                launch(Dispatchers.IO) {
                    foodAlarmServiceRepository.getFoodAlarmModel(alarmId)
                        ?.let { foodAlarmModel = it }
                }
            }
        }

        foodAlarmModel?.let { foodAlarmModel ->
            ownNotification = FoodAlarmServiceNotification(this, foodAlarmModel)
            startForeground(foodAlarmModel.alarmId, ownNotification.getNotification())

            playVibration()
            playRingtone()

            if (Settings.canDrawOverlays(this)) {
                initView()
                initOverlayParams()
                initViewListeners()
                showOverlay()
            } else navToDetail()
        }
    }

    private fun playVibration() {
        foodAlarmModel?.let {
            if (it.isVibration) vibrationPlayer.play()
        }
    }

    private fun playRingtone() {
        foodAlarmModel?.let { foodAlarmModel ->
            foodAlarmModel.ringtonePath?.let { ringtonePath ->
                ringtonePlayer.play(Uri.parse(ringtonePath))
            }
        }
    }

    private fun stop() {
        clearUI()

        foodAlarmModel?.let { foodAlarmModel ->
            with(foodAlarmModel) {
                stopForeground(alarmId)

                runBlocking {
                    launch(Dispatchers.IO) {
                        foodAlarmServiceRepository.stopFoodAlarm(alarmId)
                    }
                }
            }
        }

        stopSelf()
    }

    private fun delay() {
        clearUI()

        foodAlarmModel?.let { foodAlarmModel ->
            startForeground(
                foodAlarmModel.alarmId,
                ownNotification.getDelayNotification()
            )

            runBlocking {
                launch(Dispatchers.IO) {
                    foodAlarmServiceRepository.setAlarm(foodAlarmModel)
                }
            }
        }
    }

    private fun navToDetail() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun initView() {
        val contextThemeWrapped = ContextThemeWrapper(this, R.style.Theme_MyPet)
        val layoutInflater =
            contextThemeWrapped.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = layoutInflater.inflate(R.layout.service_food_alarm_overlay, null)

        foodAlarmModel?.let {
            buttonDelay?.isVisible = it.isDelay
        }
    }

    private fun initOverlayParams() {
        params =
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
            )

        params?.gravity = Gravity.TOP
    }

    private fun initViewListeners() {
        view?.let { view ->
            view.setOnClickListener { navToDetail() }
            buttonDelay?.setOnClickListener { delay() }
            buttonStop?.setOnClickListener { stop() }
        }
    }

    private fun showOverlay() {
        view?.let {
            windowManager.addView(view, params)
        }
    }

    private fun removeOverlay() {
        view?.let {
            windowManager.removeView(view)
        }
    }

    private fun clearUI() {
        ringtonePlayer.apply { stop() }
        vibrationPlayer.apply { stop() }
        removeOverlay()
    }

    private fun stopForegroundService() {
        foodAlarmModel?.let { foodDetailAlarmModel ->
            stopForeground(foodDetailAlarmModel.alarmId)
        }
        stopSelf()
    }

    companion object {
        const val ALARM_OVERLAY_ACTION_START = "alarm_overlay_action_start"
        const val ALARM_OVERLAY_ACTION_STOP = "alarm_overlay_action_stop"
        const val ALARM_OVERLAY_ACTION_DELAY = "alarm_overlay_action_delay"
        const val ALARM_OVERLAY_ACTION_NAV_TO_DETAIL = "alarm_overlay_action_nav_to_detail"

        const val ALARM_ID = "alarm_id"
    }
}