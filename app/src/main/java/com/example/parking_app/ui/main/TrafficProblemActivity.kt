package com.example.parking_app.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.parking_app.R

class TrafficProblemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic_problem)
    }


    companion object{
         fun launch(context: Context){
            context.startActivity(Intent(context, TrafficProblemActivity::class.java))
        }
    }
}
