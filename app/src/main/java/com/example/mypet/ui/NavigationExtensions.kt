package com.example.mypet.ui

import androidx.navigation.NavOptions
import com.example.mypet.app.R

val navOptions = NavOptions.Builder()
    .setLaunchSingleTop(true)
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_left)
    .build()