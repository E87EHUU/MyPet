package com.example.mypet.domain

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
const val POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS

fun Context.isNotGrantedPermission(permission: String) =
    when (permission) {
        POST_NOTIFICATIONS -> {
            if (Build.VERSION.SDK_INT >= 33)
                ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            else false
        }

        else -> false
    }

fun Context.isGrantedPermission(permission: String) =
    when (permission) {
        POST_NOTIFICATIONS -> {
            if (Build.VERSION.SDK_INT >= 33)
                ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            else false
        }

        else -> false
    }

fun Activity.requestPermission(permission: String) {
    when (permission) {
        POST_NOTIFICATIONS -> {
            if (Build.VERSION.SDK_INT >= 33)
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
        }
    }
}