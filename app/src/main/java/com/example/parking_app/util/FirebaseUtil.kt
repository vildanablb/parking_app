package com.example.parking_app.util

import android.widget.Toast
import com.example.parking_app.api.ParkingLot
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

object FirebaseUtil {

    var user: FirebaseUser? = null
    var auth: FirebaseAuth? = null



    fun getParkingLots(): ArrayList<ParkingLot> {
        var parkingLots: ArrayList<ParkingLot> = arrayListOf()
        FirebaseFirestore.getInstance().collection("ParkingLot")
            .get().addOnSuccessListener { result ->

                Timber.d("result parking lots " + result.documents.size)
            }
            .addOnFailureListener { error ->

                Timber.d("failed " + error.localizedMessage)

             }
        Timber.d("Parking lots " + parkingLots.size)
        return parkingLots
    }


}