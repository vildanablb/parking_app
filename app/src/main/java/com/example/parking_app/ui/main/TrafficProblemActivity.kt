package com.example.parking_app.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.parking_app.R
import com.example.parking_app.util.GridSpacingDecoration
import kotlinx.android.synthetic.main.activity_traffic_problem.*

class TrafficProblemActivity : AppCompatActivity() {

    private val categories: ArrayList<TrafficCategoryModel> by lazy { arrayListOf(
        TrafficCategoryModel(R.drawable.ic_car_collision, "collision"),
        TrafficCategoryModel(R.drawable.ic_traffic_jam, "jam"),
        TrafficCategoryModel(R.drawable.ic_speeding, "cameras"),
        TrafficCategoryModel(R.drawable.ic_patrol, "police")

    ) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic_problem)
        val trafficCategoryAdapter = TrafficCategoryAdapter()
        trafficCategoryAdapter.categories = categories
        rv_categories.adapter = trafficCategoryAdapter
        rv_categories.layoutManager = GridLayoutManager(this, 2)
        rv_categories.addItemDecoration(GridSpacingDecoration(50))
        trafficCategoryAdapter.onItemClickListener = { name: String, category: TrafficCategoryModel ->
            trafficCategoryAdapter.selectedCategory = name
        }

        submit.setOnClickListener {
            Toast.makeText(this, "Report successfully sent", Toast.LENGTH_LONG).show();
            finish()
        }

    }

    companion object{
         fun launch(context: Context){
            context.startActivity(Intent(context, TrafficProblemActivity::class.java))
        }
    }
}
