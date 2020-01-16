package com.example.parking_app.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parking_app.R
import com.example.parking_app.api.TrafficProblem
import kotlinx.android.synthetic.main.traffic_problem_item.view.*

class TrafficProblemAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    var trafficProblemList: ArrayList<TrafficProblem> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BaseViewHolder(inflater.inflate(R.layout.traffic_problem_item, parent, false))
    }

    override fun getItemCount(): Int {
        return trafficProblemList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val view = holder.itemView
        val item = trafficProblemList[position]
        view.category.text = item.category
        view.description.text = item.description
        view.date.text = item.date_reported
        view.address.text = item.address
    }


}