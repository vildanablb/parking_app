package com.example.parking_app.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.parking_app.R
import com.example.parking_app.api.ParkingLot
import com.example.parking_app.api.TrafficProblem
import com.example.parking_app.util.CustomInfoWindowMap
import com.example.parking_app.util.FirebaseUtil
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MapFragment : Fragment(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
    private var googleMap: GoogleMap? = null
    var location: Location? = null
    var mapFragment: SupportMapFragment? = null
    var parkingList: ArrayList<ParkingLot> = arrayListOf()
    var trafficProblemList: ArrayList<TrafficProblem> = arrayListOf()
    private lateinit var locationClient: FusedLocationProviderClient

    private var locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if (FirebaseUtil.location == null) {
                Toast.makeText(context, "no location provided", Toast.LENGTH_LONG).show()
            }
            FirebaseUtil.location = locationResult.lastLocation
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationClient = LocationServices.getFusedLocationProviderClient(context!!)
    }

    override fun onPause() {
        locationClient.removeLocationUpdates(locationCallback)
        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        launch {
            parkingList = FirebaseUtil.getParkingLots()
            trafficProblemList = FirebaseUtil.getTrafficProblems()
            setupMap(parkingList, trafficProblemList)
        }

        fab.setOnClickListener {
            TrafficProblemDialog().show(childFragmentManager, null)
        }

    }

    fun setupMap(
        parkinglist: ArrayList<ParkingLot>,
        trafficProblemList: ArrayList<TrafficProblem>
    ) {

        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 1
            )

        } else {
            mapFragment?.getMapAsync {
                it.isMyLocationEnabled = true
                val center = LatLng(
                    FirebaseUtil.location?.latitude ?: 0.0,
                    FirebaseUtil.location?.longitude ?: 0.0
                )
                it.uiSettings.isMyLocationButtonEnabled = true
                it.uiSettings.isMapToolbarEnabled = false
                //it.uiSettings.isZoomControlsEnabled = true
                val customInfoWindowMap = CustomInfoWindowMap(requireContext())
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 15f))
                for (parking in parkinglist) {
                    //it.setInfoWindowAdapter(customInfoWindowMap)
                    val options = MarkerOptions().apply {
                        //it.setInfoWindowAdapter(customInfoWindowMap)
                        this.position(
                            LatLng(
                                parking.lat.toDouble(),
                                parking.lon.toDouble()
                            )
                        )
                        this.icon(
                            bitmapDescriptorFromVector(
                                requireContext(),
                                R.drawable.ic_parking_marker
                            )
                        )
                    }
                    val markerOptions = MarkerOptions().position(
                        LatLng(
                            parking.lat.toDouble(),
                            parking.lon.toDouble()
                        )
                    )
                        .icon(
                            bitmapDescriptorFromVector(
                                requireContext(),
                                R.drawable.ic_parking_marker
                            )
                        )
                    val marker = it.addMarker(options)
                    marker.tag = parking

                }

                for (problem in trafficProblemList) {
                    var geo = FirebaseUtil.getLocationFromAddress(context, problem.address)
                    var icon = when (problem.category) {
                        "police" -> R.drawable.patrol_marker
                        "jam" -> R.drawable.marker_traffic
                        "cameras" -> R.drawable.marker_speed_camera
                        else -> R.drawable.crash_marker
                    }

                    if (geo != null) {
                        val options = MarkerOptions().apply {
                            this.position(LatLng(geo.latitude, geo.longitude))
                            this.icon(
                                bitmapDescriptorFromVector(
                                    requireContext(),
                                    icon
                                )
                            )
                            this.title(problem.description)
                        }

                        val marker = it.addMarker(options)
                        marker.tag = problem
                        customInfoWindowMap.trafficProblemMarkers.add(marker.id)


                    }
                }

                it.setInfoWindowAdapter(customInfoWindowMap)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 1 && grantResults.fold(true) { sum, result -> sum && result == PackageManager.PERMISSION_GRANTED }) {
            locationClient.requestLocationUpdates(LocationRequest.create().apply {
                interval = 10000
            }, locationCallback, null)
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    override fun onResume() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationClient.lastLocation.addOnSuccessListener {
                if (FirebaseUtil.location == null && it != null) {
                }
                FirebaseUtil.location = it
                setupMap(parkingList, trafficProblemList)
            }
            locationClient.requestLocationUpdates(LocationRequest.create().apply {
                interval = 10000
            }, locationCallback, null)
        }
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }


}
