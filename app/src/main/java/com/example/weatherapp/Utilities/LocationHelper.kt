package com.example.weatherapp.Utilities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.example.weatherforecast.sharedprefernces.SharedPreferencesHelper
import com.google.android.gms.location.*
import kotlin.math.log

class LocationHelper(private val context: Context) {
    private lateinit var fusedClient: FusedLocationProviderClient
    private var isTakeLocation: Boolean = false
    private var currentLat = ""
    private var currentLong = ""
    val PERMISSION_ID = 5005

    fun checkPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }

    fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    fun getLocation(activity: Activity) {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                requestNewLocationData(activity)
            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(intent)
            }
        } else {
            requestPermissions(activity)
        }
    }

    private fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(activity: Activity) {
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        fusedClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedClient.requestLocationUpdates(
            locationRequest,
            locationCallBack,
            Looper.myLooper()
        )
    }

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation(callback: (Location) -> Unit) {
        if (checkPermissions() && isLocationEnabled()) {
            fusedClient = LocationServices.getFusedLocationProviderClient(context)
            fusedClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    callback(location)
                }
            }
        }
    }

    private val locationCallBack: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            if (!isTakeLocation) {
                isTakeLocation = true
                val lastLocation: Location? = p0.lastLocation
                currentLong = lastLocation?.longitude.toString()
                currentLat = lastLocation?.latitude.toString()
                SharedPreferencesHelper.getInstance(context).let {
                    it.saveCurrentLocation("lat", lastLocation?.latitude)
                    it.saveCurrentLocation("long", lastLocation?.longitude)
                }
            }
        }
    }
}