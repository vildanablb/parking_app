package com.example.parking_app.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.widget.Toast
import com.example.parking_app.api.ParkingLot
import com.example.parking_app.api.TrafficProblem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await
import timber.log.Timber


object FirebaseUtil {

    var user: FirebaseUser? = null
    var auth: FirebaseAuth? = null
    var location: Location? = null


    suspend fun getParkingLots(): ArrayList<ParkingLot> {
        var parkingLots: ArrayList<ParkingLot> = arrayListOf()
        try {
            val snapshot = FirebaseFirestore.getInstance().collection("ParkingLot").get().await()
            for (document in snapshot) {
                parkingLots.add(document.toObject(ParkingLot::class.java))
            }

            return parkingLots
        } catch (e: FirebaseFirestoreException) {
            Timber.d("ERROR " + e.localizedMessage)
        }
        return parkingLots
    }

    suspend fun getTrafficProblems(): ArrayList<TrafficProblem> {
        var trafficProblems: ArrayList<TrafficProblem> = arrayListOf()

        try {
            val snapshot =
                FirebaseFirestore.getInstance().collection("TrafficProblem").get().await()
            for (document in snapshot) {
                trafficProblems.add(document.toObject(TrafficProblem::class.java))
            }
            return trafficProblems

        } catch (e: Exception) {
            Timber.d("ERROR " + e.localizedMessage)
        }

        return trafficProblems
    }

    fun getLocationFromAddress(context: Context?, addressName: String): GeoPoint? {
        var coder = Geocoder(context)
        var address: List<Address>

        try {
            address = coder.getFromLocationName(addressName, 5)
            if (address == null) {
                return null
            }
            var location: Address = address.get(0)
            return GeoPoint(location.latitude, location.longitude)

        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Unable to retrieve location from given address",
                Toast.LENGTH_SHORT
            ).show()
        }
        return null
    }

    suspend fun addTrafficProblem(trafficProblem: TrafficProblem) {
        try {
            FirebaseFirestore.getInstance().collection("TrafficProblem").add(trafficProblem).await()

        } catch (e: Exception) {
            Timber.d("ERROR " + e.localizedMessage)
        }
    }

    suspend fun updateParkingStatus(parkingLot: ParkingLot, isWorking: Boolean, userRating: Int ? = null) {
        val userMap: HashMap<String, Any> = HashMap()
        val newRating: ArrayList<Int> = parkingLot.rating
        if(userRating != null)
        newRating.add(userRating)
        userMap["parked_car"] = isWorking
        userMap["rating"] = newRating
        try {
            FirebaseFirestore.getInstance().collection("ParkingLot").document(parkingLot.parking_id).update(userMap).await()

        } catch (e: Exception) {
            Timber.d("ERROR " + e.localizedMessage)
        }
    }


}