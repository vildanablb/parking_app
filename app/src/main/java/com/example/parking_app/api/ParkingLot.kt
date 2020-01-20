package com.example.parking_app.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParkingLot(
    val parking_id: String = "",
    val parking_name: String = "",
    val address: String = "",
    val capacity: Int = 0,
    val lon: String = "",
    val lat: String = "",
    val all_day: Boolean? = false,
    val surveillance: Boolean = false,
    val guard: Boolean = false,
    val price: String = "",
    val parked_car: Boolean = false,
    val rating: ArrayList<Int> = arrayListOf()
) : Parcelable



