package com.example.mypet.ui.alarm.repeat.service

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
import androidx.recyclerview.widget.RecyclerView
import com.example.mypet.app.R
import com.example.mypet.domain.AlarmServiceRepository
import com.example.mypet.domain.food.alarm.FoodAlarmModel
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
    lateinit var foodAlarmServiceRepository: AlarmServiceRepository

    private var windowManager: WindowManager? = null
    private var view: View? = null
    private var params: WindowManager.LayoutParams? = null

    private val buttonDelay
        get() = view?.findViewById<Button>(R.id.buttonAlarmDelay)
    private val buttonStop
        get() = view?.findViewById<Button>(R.id.buttonViewFoodAlarmOverlayStop)
    private val recycler
        get() = view?.findViewById<RecyclerView>(R.id.recyclerViewFoodAlarmOverlay)

    private val foodAlarmModels = mutableMapOf<Int, FoodAlarmModel>()
    private lateinit var ownNotification: FoodAlarmServiceNotification
    private val ringtonePlayer: RingtonePlayer = RingtonePlayer(this)
    private val vibrationPlayer: VibrationPlayer = VibrationPlayer(this)
    private val adapter = FoodAlarmServiceAdapter()

    override fun onBind(intent: Intent) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (intent.action) {
                ALARM_OVERLAY_ACTION_START -> start(intent)
                ALARM_OVERLAY_ACTION_STOP -> stop(intent)
                ALARM_OVERLAY_ACTION_DELAY -> delay(intent)
                ALARM_OVERLAY_ACTION_NAV_TO_DETAIL -> navToDetail()
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

        ringtonePlayer.onDestroy()
        vibrationPlayer.onDestroy()

        foodAlarmModels.clear()
        windowManager = null
    }

    private fun start(intent: Intent) {
        val alarmId = intent.getIntExtra(ALARM_ID, 0)
        if (alarmId < 0) return

        println(alarmId)
        println(foodAlarmModels)

        if (!foodAlarmModels.containsKey(alarmId)) {
            runBlocking {
                launch(Dispatchers.IO) {
                    foodAlarmServiceRepository.getFoodAlarmModel(alarmId)
                        ?.let { foodAlarmModels[alarmId] = it }
                }
            }
        }

        foodAlarmModels[alarmId]?.let { foodAlarmModel ->
            ownNotification = FoodAlarmServiceNotification(this, foodAlarmModel)
            startForeground(foodAlarmModel.alarmId, ownNotification.getNotification())

            playVibration(foodAlarmModel)
            playRingtone(foodAlarmModel)

            if (Settings.canDrawOverlays(this)) {
                initView(foodAlarmModel)
                initOverlayParams()
                initViewListeners(intent)
                showOverlay()
                adapter.submitList(foodAlarmModels.map { it.value })
            } else navToDetail()
        }
    }

    private fun playVibration(foodAlarmModel: FoodAlarmModel) {
        if (foodAlarmModel.isVibration) vibrationPlayer.play()
    }

    private fun playRingtone(foodAlarmModel: FoodAlarmModel) {
        foodAlarmModel.ringtonePath?.let { ringtonePath ->
            ringtonePlayer.play(Uri.parse(ringtonePath))
        }
    }

    private fun stop(intent: Intent) {
        val alarmId = intent.getIntExtra(ALARM_ID, 0)

        clearUI()

        foodAlarmModels[alarmId]?.let { foodAlarmModel ->
            with(foodAlarmModel) {
                stopForeground(STOP_FOREGROUND_REMOVE)

                runBlocking {
                    launch(Dispatchers.IO) {
                        foodAlarmServiceRepository.stopFoodAlarm(alarmId)
                    }
                }
            }
        }

        stopSelf()
    }

    private fun delay(intent: Intent) {
        val alarmId = intent.getIntExtra(ALARM_ID, 0)

        clearUI()

        foodAlarmModels[alarmId]?.let { foodAlarmModel ->
            startForeground(
                foodAlarmModel.alarmId,
                ownNotification.getDelayNotification()
            )

            runBlocking {
                launch(Dispatchers.IO) {
                    foodAlarmServiceRepository.setDelayAlarm(foodAlarmModel)
                }
            }
        }
    }

    private fun navToDetail() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun initView(foodAlarmModel: FoodAlarmModel) {
        if (view == null) {
            windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

            val contextThemeWrapped = ContextThemeWrapper(this, R.style.Theme_MyPet)
            val layoutInflater =
                contextThemeWrapped.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.service_alarm_overlay, null)
            recycler?.adapter = adapter

            buttonDelay?.isVisible = foodAlarmModel.isDelay
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

    private fun initViewListeners(intent: Intent) {
        view?.let { view ->
            view.setOnClickListener { navToDetail() }
            buttonDelay?.setOnClickListener { delay(intent) }
            buttonStop?.setOnClickListener { stop(intent) }
        }
    }

    private fun showOverlay() {
        if (foodAlarmModels.size == 1)
            windowManager?.addView(view, params)
    }

    private fun removeOverlay() {
        windowManager?.removeView(view)
        params = null
        view = null
    }

    private fun clearUI() {
        ringtonePlayer.apply { stop() }
        vibrationPlayer.apply { stop() }
        removeOverlay()
    }

    companion object {
        const val ALARM_OVERLAY_ACTION_START = "alarm_overlay_action_start"
        const val ALARM_OVERLAY_ACTION_STOP = "alarm_overlay_action_stop"
        const val ALARM_OVERLAY_ACTION_DELAY = "alarm_overlay_action_delay"
        const val ALARM_OVERLAY_ACTION_NAV_TO_DETAIL = "alarm_overlay_action_nav_to_detail"

        const val ALARM_ID = "alarm_id"
    }
}