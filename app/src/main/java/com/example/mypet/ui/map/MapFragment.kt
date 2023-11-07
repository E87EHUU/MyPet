package com.example.mypet.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentMapBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition

class MapFragment : Fragment() {

    private var _ui: FragmentMapBinding? = null
    private val ui get() = _ui!!
    private val map by lazy { ui.mapView.mapWindow.map }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            initMap(Point(location.latitude, location.longitude))
        }
        override fun onProviderEnabled(provider: String) {
            view?.snackMessage(getString(R.string.location_enabled))
        }
        override fun onProviderDisabled(provider: String) {
            view?.snackMessage(getString(R.string.location_disabled))
        }
    }

    companion object {
        private const val REQUEST_CODE = 333
        private const val LOCATION_REFRESH_PERIOD = 2000L
        private const val LOCATION_DISTANCE = 100f
        private val DEFAULT_LOCATION = Point(55.769366, 37.583721)
        private const val DEFAULT_ZOOM = 16.0f
        private const val AZIMUTH = 0f
        private const val TILT = 0f

        fun newInstance() = MapFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _ui = FragmentMapBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED -> { getCurrentLocation() }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                explanationDialog()
            }
            else -> { requestPermission() }
        }
    }

    @Suppress("DEPRECATION")
    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                var grantedPermissions = 0
                if (grantResults.isNotEmpty()) {
                    for (result in grantResults) {
                        if (result == PackageManager.PERMISSION_GRANTED) {
                            grantedPermissions++
                        }
                    }
                    if (grantResults.size == grantedPermissions) {
                        getLocation()
                    } else {
                        detailsDialog(getString(R.string.location_not_granted), getString(R.string.allow_location))
                        initMap(DEFAULT_LOCATION)
                    }
                } else {
                    detailsDialog(getString(R.string.location_not_granted), getString(R.string.allow_location))
                    initMap(DEFAULT_LOCATION)
                }
                return
            }
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                @Suppress("deprecation")
                val providerGps = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                providerGps?.let {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        LOCATION_REFRESH_PERIOD,
                        LOCATION_DISTANCE,
                        locationListener
                    )

                }
            } else {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location == null) {
                    detailsDialog(getString(R.string.location_is_off), getString(R.string.turn_location_on))
                    initMap(DEFAULT_LOCATION)
                } else {
                    initMap(Point(location.latitude, location.longitude))
                    detailsDialog(getString(R.string.location_is_off), getString(R.string.turn_location_on))
                }
            }
        } else {
            explanationDialog()
        }
    }

    private fun explanationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.geolocation_access))
            .setMessage(getString(R.string.need_access_to_location))
            .setPositiveButton(getString(R.string.allow_access)) { _, _ -> requestPermission() }
            .setNegativeButton(getString(R.string.deny_access)) { dialog, _ ->
                dialog.cancel()
                initMap(DEFAULT_LOCATION)
            }
            .create()
            .show()
    }

    private fun initMap(location: Point) {
        map.move(CameraPosition(location, DEFAULT_ZOOM, AZIMUTH, TILT))
    }

    private fun detailsDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun View.snackMessage(text: String, length: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(this, text, length).show()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        ui.mapView.onStart()
    }

    override fun onStop() {
        ui.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        _ui = null
        super.onDestroyView()
    }
}