package com.example.parking_app.util

import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import android.app.Activity
import android.content.Context
import com.example.parking_app.R
import com.example.parking_app.api.ParkingLot
import kotlinx.android.synthetic.main.marker_dialog.view.*


class CustomInfoWindowMap : GoogleMap.InfoWindowAdapter {
    private var context: Context? = null

    constructor(context: Context) {
        this.context = context
    }


    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View {
        val view = (context as Activity).layoutInflater
            .inflate(R.layout.marker_dialog, null)

        val item = marker.tag as ParkingLot?

        view.address.text = item?.address
        view.capacity.text = item?.capacity.toString()
        view.price.text = item?.price + " BAM/h"

        return view
    }
}