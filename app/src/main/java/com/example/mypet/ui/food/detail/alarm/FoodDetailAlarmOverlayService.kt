package com.example.mypet.ui.food.detail.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PixelFormat
import android.net.Uri
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.NotificationCompat
import com.example.mypet.app.R
import com.example.mypet.ui.MainActivity

class FoodDetailAlarmOverlayService : Service() {
    private lateinit var context: Context

    private lateinit var wm: WindowManager
    private var view: View? = null
    private var params: WindowManager.LayoutParams? = null

    private val buttonStop
        get() = view?.findViewById<Button>(R.id.buttonAlarmStop)


    override fun onBind(intent: Intent) = null

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            startForeground(ONGOING_NOTIFY_ID, getForegroundNotification(it))
        }

        wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        initView()
        initOverlayParams()
        initViewListeners()
        showOverlay()

        return super.onStartCommand(intent, flags, startId)
    }

    private fun initViewListeners() {
        view?.let { view ->
            buttonStop?.setOnClickListener {
                wm.removeView(view)
            }
        }
    }

    private fun initView() {
        val contextThemeWrapped = ContextThemeWrapper(this, R.style.Theme_MyPet)
        val layoutInflater =
            contextThemeWrapped.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = layoutInflater.inflate(R.layout.overlay_alarm, null)
    }


    private fun initOverlayParams() {
        params =
            WindowManager.LayoutParams(
                Resources.getSystem().displayMetrics.widthPixels,
                500,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
            )

        params?.gravity = Gravity.START or Gravity.TOP
    }

    private fun showOverlay() {
        wm.addView(view, params)
    }

    private fun hideOverlay() {
        wm.removeView(view)
    }

    fun getForegroundNotification(intent: Intent): Notification? {
        val chanelId = context.packageName
        val notificationId = intent.getIntExtra(FoodDetailAlarmReceiver.NOTIFICATION_ID, 0)
        val title = intent.getStringExtra(FoodDetailAlarmReceiver.TITLE)
        val description = intent.getStringExtra(FoodDetailAlarmReceiver.DESCRIPTION)
        val icon = intent.getIntExtra(FoodDetailAlarmReceiver.ICON, R.mipmap.ic_launcher)
        val isDelay = intent.getBooleanExtra(FoodDetailAlarmReceiver.IS_DELAY, false)

        createNotificationChannel(chanelId, intent)

        /*        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) return */

        val pendingIntentStartMainActivity =
            let {
                PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }

        val pendingIntentStartServiceToDelayAlert = pendingIntentStartMainActivity
        val pendingIntentStartServiceToStopAlert = pendingIntentStartMainActivity

        val notification = NotificationCompat.Builder(context, chanelId)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(icon)
            .setAutoCancel(true)
            .setOngoing(true)
            .setContentIntent(pendingIntentStartMainActivity)
            .apply {
                if (isDelay)
                    addAction(
                        R.drawable.baseline_repeat_24,
                        context.getString(R.string.notification_chanel_action_delay),
                        pendingIntentStartServiceToDelayAlert
                    )
            }
            .addAction(
                R.drawable.baseline_close_24,
                context.getString(R.string.notification_chanel_action_stop),
                pendingIntentStartServiceToStopAlert
            )
            .build()

//        notification.flags = NotificationCompat.FLAG_FOREGROUND_SERVICE

        return notification
    }

    private fun createNotificationChannel(id: String, intent: Intent) {
        val name = context.getString(R.string.app_name)
        val melody = Uri.parse(intent.getStringExtra(FoodDetailAlarmReceiver.MELODY))
            ?: Settings.System.DEFAULT_ALARM_ALERT_URI
        val isVibration = intent.getBooleanExtra(FoodDetailAlarmReceiver.IS_VIBRATION, false)

        val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_NONE)
            .apply {
                enableVibration(isVibration)
                setSound(melody, audioAttributes)
                lightColor = Color.BLUE
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val ONGOING_NOTIFY_ID = 7654
    }
}