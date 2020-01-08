package com.example.parking_app.util

import android.widget.Toast
import com.example.parking_app.api.ParkingLot
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await
import timber.log.Timber


object FirebaseUtil {

    var user: FirebaseUser? = null
    var auth: FirebaseAuth? = null


    suspend fun getParkingLots(): ArrayList<ParkingLot> {
        var parkingLots: ArrayList<ParkingLot> = arrayListOf()
        try {
            val snapshot = FirebaseFirestore.getInstance().collection("ParkingLot").get().await()
            for (document in snapshot) {
                parkingLots.add(document.toObject(ParkingLot::class.java))
            }

            return parkingLots
        } catch (e: FirebaseFirestoreException) {
            Timber.d("ERROR")
        }
        return parkingLots
    }



}