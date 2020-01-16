package com.example.parking_app.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrafficProblem(
    val category: String = "",
    val description: String = "",
    val address: String = "",
    val date_reported: String = ""
) : Parcelable