package com.example.codeidapp.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.codeidapp.utils.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

// Source: Android Location Manager with Kotlin Flows
// URL: https://medium.com/@psarakisnick/android-location-manager-with-kotlin-flows-082c992d1b31
// Author: Nick Psarakis
// LocationManager untuk dptin GPS ini sumber yang saya gunakan

class LocationManagerImpl(
    private val context: Context
) : LocationManager {

    private val client: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    @Throws
    override fun listenToLocation(): Flow<Location> {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,5000L)
            .setMinUpdateDistanceMeters(1000F)
            .build()
        return callbackFlow {
            if (!hasLocationPermission()) throw NoPermissionsException
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.lastLocation?.let {
                        launch {
                            send(it)
                        }
                    }
                }
            }

            client.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())

            awaitClose {
                client.removeLocationUpdates(locationCallback)
            }

        }
    }

        override fun hasLocationPermission(): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }

    }

    object NoPermissionsException : Exception()