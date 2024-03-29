package com.example.parking_app.ui.main

import android.content.res.ColorStateList
import android.graphics.Color
import android.location.Location
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.set
import androidx.recyclerview.widget.RecyclerView
import com.example.parking_app.R
import com.example.parking_app.api.ParkingLot
import kotlinx.android.synthetic.main.parking_lot_item.view.*
import java.math.RoundingMode
import kotlin.math.*
import androidx.appcompat.app.AppCompatActivity


class ParkingListAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    var parkings: ArrayList<ParkingLot>? = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var isWorking = false
    var location: Location? = null
    var onItemClickListener: ((parkingLot: ParkingLot?, position: Int, isWorking: Boolean) -> Unit)? = null
    private val dropdownOpen = SparseBooleanArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            -1 -> LoaderViewHolder(inflater.inflate(R.layout.lazy_loader_spinner, parent, false))
            else -> BaseViewHolder(inflater.inflate(R.layout.parking_lot_item, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return parkings?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val view = holder.itemView
        val item = parkings?.get(position)
        view.parking_name.text = item?.parking_name
        val location = location
        when {
            location != null -> {
                view.distance.text = "${distance(
                    location.latitude, location.longitude, item?.lat?.toDouble()
                        ?: 0.0, item?.lon?.toDouble() ?: 0.0
                ).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()} km"
            }

            else -> {
                view.distance.text = item?.address
            }
        }

        isWorking = item?.parked_car ?: false
        if(isWorking)
            view.btn_park.text = "Stop Parking"
        else{
            view.btn_park.text = "Park here"
        }
        view.address.text = item?.address
        view.capacity.text = item?.capacity.toString()
        view.price.text = item?.price + " BAM/h"
        view.available_indicator.imageTintList = ColorStateList.valueOf(
            when{
                item?.available == true -> Color.parseColor("#a8e2bf")
                else -> Color.parseColor("#e16969")
            }
        )
        view.surveillance.setCompoundDrawablesRelativeWithIntrinsicBounds(
            R.drawable.ic_surveillance_video_camera,
            0,
            when (item?.surveillance) {
                true -> R.drawable.ic_ticker
                else -> R.drawable.ic_error
            },
            0
        )
        view.guard.setCompoundDrawablesRelativeWithIntrinsicBounds(
            R.drawable.ic_policeman,
            0,
            when (item?.guard) {
                true -> R.drawable.ic_ticker
                else -> R.drawable.ic_error
            },
            0
        )
        view.all_day.setCompoundDrawablesRelativeWithIntrinsicBounds(
            R.drawable.ic_all_day,
            0,
            when (item?.all_day) {
                true -> R.drawable.ic_ticker
                else -> R.drawable.ic_error
            },
            0
        )

        if (dropdownOpen[position]) {
            view.info_container.visibility = View.VISIBLE
            view.arrow.rotation = 90f
        } else {
            view.info_container.visibility = View.GONE
            view.rotation = 0f
        }
        view.main_container.setOnClickListener {
            dropdownOpen[holder.adapterPosition] = !dropdownOpen[holder.adapterPosition]
            notifyItemChanged(holder.adapterPosition)
            //onItemClickListener?.invoke(item, holder.adapterPosition)
        }

        if (!item?.rating.isNullOrEmpty()) {
            val rating = (item?.rating!!.sum().toDouble() / item.rating.size).toBigDecimal()
                .setScale(1, RoundingMode.UP).toDouble()
            view.rating.text = "Rating: " + rating + "/5";
        }

        view.btn_park.setOnClickListener {
            isWorking = if (!isWorking) {
                onItemClickListener?.invoke(
                    item,
                    holder.adapterPosition,
                    true
                )
                view.btn_park.text = "Stop Parking"
                true
            } else {
                onItemClickListener?.invoke(
                    item,
                    holder.adapterPosition,
                    false
                )
                view.btn_park . text = "Park here"
                false
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == parkings?.size) {
            if (parkings?.isEmpty() == true) {
                return -1
            }
        }
        return 0
    }

}

fun distance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
    val earthRadius = 6371.0 //kilometers
    val dLat = Math.toRadians(lat2 - lat1)
    val dLng = Math.toRadians(lng2 - lng1)
    val a = sin(dLat / 2) * sin(dLat / 2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLng / 2) * sin(dLng / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return earthRadius * c
}

class LoaderViewHolder(itemView: View) : BaseViewHolder(itemView)
