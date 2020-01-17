package com.example.parking_app.util

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Button
import androidx.core.view.ViewCompat
import com.example.parking_app.R

fun Button.disable() {
    isClickable = false
    ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(Color.parseColor("#b4b4b4")))
}

fun Button.enable() {
    isClickable = true
    ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(Color.parseColor("#3db3c3")))
}

