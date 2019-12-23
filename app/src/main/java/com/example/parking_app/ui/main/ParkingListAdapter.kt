package com.example.parking_app.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parking_app.R
import com.example.parking_app.api.ParkingLot
import kotlinx.android.synthetic.main.parking_lot_item.view.*

class ParkingListAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    var parkings: ArrayList<ParkingLot> = arrayListOf();
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context);

        return BaseViewHolder(inflater.inflate(R.layout.parking_lot_item, parent, false))
    }

    override fun getItemCount(): Int {
        return parkings.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val view = holder.itemView
        val item = parkings[position]
        view.parking_name.text = item.parking_name

    }
}