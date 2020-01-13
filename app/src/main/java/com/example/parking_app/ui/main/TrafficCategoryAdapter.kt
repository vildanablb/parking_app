package com.example.parking_app.ui.main

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parking_app.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.category_traffic_item.view.*

class TrafficCategoryAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    var categories: ArrayList<TrafficCategoryModel> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var selectedCategory: String = ""
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var onItemClickListener: ((name: String, category: TrafficCategoryModel) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_traffic_item, parent, false)
        return BaseViewHolder(view)
    }

    override fun getItemCount(): Int {
       return categories.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = categories[position]
        val view = holder.itemView

        if(!selectedCategory.isNullOrEmpty()){
            if(selectedCategory == item.name){
                view.checked.visibility = View.VISIBLE
            }
            else{
                view.checked.visibility = View.GONE
            }
        }
        view.icon.setImageResource(item.icon)
        view.name.text = item.name

        view.setOnClickListener { onItemClickListener?.invoke(item.name, item) }
    }
}

@Parcelize
data class TrafficCategoryModel(val icon: Int, val name: String) : Parcelable
