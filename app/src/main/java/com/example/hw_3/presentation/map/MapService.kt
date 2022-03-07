package com.example.hw_3.presentation.map

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationRequest.create
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@SuppressLint("MissingPermission")
class MapService(private val context: Context) {

    private var location: Location? = null
    private val locationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationEnable =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val isLocationEnabled: Boolean
        get() {
            return locationEnable.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationEnable.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

    @ExperimentalCoroutinesApi
    fun locationFlow(): Flow<Location> = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(l: LocationResult) {
                location = l.lastLocation
                trySend(l.lastLocation)
            }
        }

        val request = create().apply {
            interval = 10000L
            priority = PRIORITY_HIGH_ACCURACY
        }

        locationClient.requestLocationUpdates(request, callback, Looper.getMainLooper())

        awaitClose {
            locationClient.removeLocationUpdates(callback)
            location = null
        }
    }
}