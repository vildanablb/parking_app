package com.example.parking_app.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParkingLot ( val parking_name: String = "",
                        val capacity: Int = 0,
                        val lon: String = "",
                        val lat: String = "",
                        val all_day: Boolean? = false,
                        val surveillance: Boolean = false,
                        val guard: Boolean = false): Parcelable



