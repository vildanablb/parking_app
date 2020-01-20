package com.example.parking_app.util

import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import android.content.Context
import android.view.LayoutInflater
import com.example.parking_app.R
import com.example.parking_app.api.ParkingLot
import com.example.parking_app.api.TrafficProblem
import kotlinx.android.synthetic.main.marker_dialog.view.*
import kotlinx.android.synthetic.main.marker_dialog.view.address
import kotlinx.android.synthetic.main.marker_dialog.view.all_day
import kotlinx.android.synthetic.main.marker_dialog.view.capacity
import kotlinx.android.synthetic.main.marker_dialog.view.guard
import kotlinx.android.synthetic.main.marker_dialog.view.price
import kotlinx.android.synthetic.main.marker_dialog.view.surveillance
import kotlinx.android.synthetic.main.parking_lot_item.view.*
import kotlinx.android.synthetic.main.traffic_problem_item.view.*


class CustomInfoWindowMap : GoogleMap.InfoWindowAdapter {
    private var context: Context? = null
    var trafficProblemMarkers: ArrayList<String> = arrayListOf();
    constructor(context: Context) {
        this.context = context
    }


    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View {
        val inflater = LayoutInflater.from(context)
        if(trafficProblemMarkers.contains(marker.id)){
            val view =  inflater.inflate(R.layout.traffic_problem_item,null)
            val item = marker.tag as TrafficProblem?

            view.category.text = item?.category
            view.description.text = item?.description
            view.date.text = item?.date_reported
            view.address.text = item?.address

            return view

        }
        else{
            val view = inflater.inflate(R.layout.marker_dialog,null)
            val item = marker.tag as ParkingLot?
            view.address.text = item?.address
            view.capacity.text = item?.capacity.toString()
            view.price.text = item?.price + " BAM/h"

            view.surveillance.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_surveillance_video_camera,
                0,
                if (item?.surveillance == true) {
                    R.drawable.ic_ticker
                } else {
                    R.drawable.ic_error
                },
                0
            )
            view.guard.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_policeman,
                0,
                when (item?.guard) {
                    true -> R.drawable.ic_ticker
                    else -> R.drawable.ic_error
                },
                0
            )
            view.all_day.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_all_day,
                0,
                when (item?.all_day) {
                    true -> R.drawable.ic_ticker
                    else -> R.drawable.ic_error
                },
                0
            )

            return view
        }


    }
}