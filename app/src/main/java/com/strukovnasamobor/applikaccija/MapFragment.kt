package com.strukovnasamobor.applikaccija

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import java.io.*
import java.lang.Math.random
import java.lang.Math.sqrt
import kotlin.math.pow


class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var LocationArray = arrayOfNulls<LatLng>(200)
    private var LocationCount = 0
    private var laScam: Double = 0.0
    private var lonScam: Double = 0.0


    //val output: FileOutputStream = context!!.openFileOutput("latlngpoints.txt", Context.MODE_PRIVATE)
    //private val input: FileInputStream = context!!.openFileInput("latlngpoints.txt")


    var currentLocation: LatLng = LatLng(20.5, 78.9)
    companion object{
        const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val ai: ApplicationInfo = activity?.applicationContext?.packageManager
            ?.getApplicationInfo(activity?.applicationContext!!.packageName, PackageManager.GET_META_DATA)
            ?: ApplicationInfo()
        val value = ai.metaData["com.google.android.geo.API_KEY"]
        val apiKey = value.toString()

        if (!Places.isInitialized()) {
            Places.initialize(this.context, apiKey)
        }

        fusedLocationClient = this.activity?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }!!
    }

    override fun onMapReady(mapObj: GoogleMap) {
        Toast.makeText(context, "Map is readey", Toast.LENGTH_SHORT).show()
        mMap = mapObj
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener (this)
        val rnd = (0..50).random()
        /*mMap.setOnMapClickListener (object :GoogleMap.OnMapClickListener{
            override fun onMapClick(markerCoords: LatLng) {
                val loc = LatLng(markerCoords.latitude, markerCoords.longitude)
                mMap.addMarker(MarkerOptions().position(loc))
            }
        })*/
        setUpMap()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val v: View = inflater.inflate(R.layout.fragment_map, container, false)
        val mapShi = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        mapShi?.getMapAsync (this)
        val bits = v.findViewById(R.id.currentLoc) as Button
        bits.setOnClickListener {
            getLastLocation()
        }
        return v
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))
                    }
                }
            } else {
                Toast.makeText(this.context, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        fusedLocationClient = this.activity?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }!!
        Looper.myLooper()?.let {
            fusedLocationClient.requestLocationUpdates(
                mLocationRequest, locationCallback,
                it
            )
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            currentLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)
        }
    }

    private fun checkPermissions(): Boolean {
        if (this.context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) } == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this.context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        this.activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                42
            )
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = context!!.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    private fun setUpMap() {
        if (this.context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && this.context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
            return
        }
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
            if(location != null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                laScam=location.latitude
                lonScam=location.longitude
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))
                val latScam = LatLng(laScam + 0.001, lonScam + 0.009)
                val latScam2 = LatLng(laScam - 0.001, lonScam + 0.009)
                val latScam3 = LatLng(laScam + 0.001, lonScam - 0.009)
                mMap.addMarker(MarkerOptions().position(latScam).title("F. Maksić").snippet("Bog mater\nMesar"))
                mMap.addMarker(MarkerOptions().position(latScam2).title("Jeliq").snippet("Radim WC"))
                mMap.addMarker(MarkerOptions().position(latScam3).title("zLaTko").snippet("Talijan"))
            }
        }
    }

    override fun onMarkerClick(p0: Marker) = false

    private fun findNearJobs(){
        for (i in LocationArray){
            if (i != null) {
                if(sqrt((currentLocation.latitude - i.latitude).pow(2.0) + (currentLocation.longitude - i.longitude).pow(2.0)) < 400){
                    //marker.isActive(hidden)
                }
            }
        }
    }
}