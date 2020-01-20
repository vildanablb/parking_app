package com.example.parking_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parking_app.R
import com.example.parking_app.api.ParkingLot
import com.example.parking_app.ui.main.MainActivity.Companion.launch
import com.example.parking_app.util.FirebaseUtil
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_parking_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class ParkingListFragment : Fragment(), CoroutineScope {


    override val coroutineContext: CoroutineContext = Dispatchers.Main
    var parkingLots: ArrayList<ParkingLot> = arrayListOf()
    private val parkingListAdapter = ParkingListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_parking_list, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        launch {
            parkingLots = FirebaseUtil.getParkingLots()
            parkingListAdapter.parkings = parkingLots
            parkingListAdapter.location = FirebaseUtil.location
            rv_parking_list.adapter = parkingListAdapter
            rv_parking_list.layoutManager = LinearLayoutManager(context)

        }

        parkingListAdapter.onItemClickListener = { source, _,isWorking ->
            if (source != null) {
                if(!isWorking)
                RateParkingDialog.create(source).show(childFragmentManager,null)
                else{
                    launch {
                        FirebaseUtil.updateParkingStatus(source, true )
                    }
                }
            }
        }

    }

}