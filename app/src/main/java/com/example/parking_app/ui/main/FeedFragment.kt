package com.example.parking_app.ui.main

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parking_app.R
import com.example.parking_app.api.TrafficProblem
import com.example.parking_app.util.FirebaseUtil
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FeedFragment : Fragment(), CoroutineScope{
    override val coroutineContext: CoroutineContext = Dispatchers.Main
    var trafficProblems: ArrayList<TrafficProblem> = arrayListOf()
    var trafficAdapter = TrafficProblemAdapter()
    var geoPoint: GeoPoint? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            trafficProblems = FirebaseUtil.getTrafficProblems()
            trafficAdapter.trafficProblemList = trafficProblems
            rv_feed.adapter = trafficAdapter
            rv_feed.layoutManager = LinearLayoutManager(context)
        }


        fab.setOnClickListener {
            TrafficProblemDialog().show(childFragmentManager, null)
        }

    }
}