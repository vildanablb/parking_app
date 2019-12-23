package com.example.parking_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.parking_app.R
import com.example.parking_app.util.FirebaseUtil
import kotlinx.android.synthetic.main.fragment_parking_list.*

class ParkingListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_parking_list, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val parkingListAdapter = ParkingListAdapter()
        rv_parking_list.adapter = parkingListAdapter
        parkingListAdapter.parkings = FirebaseUtil.getParkingLots()
        parkingListAdapter.notifyDataSetChanged()

        Toast.makeText(context, "Getting parking lots " + parkingListAdapter.parkings.size, Toast.LENGTH_LONG).show()


    }
}