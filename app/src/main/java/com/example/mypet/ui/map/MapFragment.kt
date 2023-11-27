package com.example.mypet.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentMapBinding
import com.example.mypet.domain.map.MapSearchResponseItem
import com.example.mypet.domain.map.MapSearchState
import com.example.mypet.ui.snackMessage
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yandex.mapkit.Animation
import com.yandex.mapkit.GeoObject
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MapFragment : Fragment(R.layout.fragment_map) {

    private val binding by viewBinding(FragmentMapBinding::bind)
    private val map by lazy { binding.mapViewMap.mapWindow.map }
    private val viewModel: MapViewModel by viewModels()
    private lateinit var editQueryTextWatcher: TextWatcher

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            initMap(Point(location.latitude, location.longitude))
        }
        override fun onProviderEnabled(provider: String) {
            view?.snackMessage(getString(R.string.map_location_enabled))
        }
        override fun onProviderDisabled(provider: String) {
            view?.snackMessage(getString(R.string.map_location_disabled))
        }
    }

    private val cameraListener = CameraListener { _, _, reason, _ ->
        if (reason == CameraUpdateReason.GESTURES) {
            viewModel.setVisibleRegion(map.visibleRegion)
        }
    }

    private val searchResultPlacemarkTapListener = MapObjectTapListener { mapObject, _ ->
        val selectedObject = (mapObject.userData as? GeoObject)
        viewModel.uiState(selectedObject!!).let {
            detailsDialog(it.first, it.second)
        }
        true
    }

    companion object {
        private const val REQUEST_CODE = 333
        private const val LOCATION_REFRESH_PERIOD = 2000L
        private const val LOCATION_DISTANCE = 100f
        private val DEFAULT_LOCATION = Point(55.769366, 37.583721)
        private const val DEFAULT_ZOOM = 16.0f
        private const val AZIMUTH = 0f
        private const val TILT = 0f
        private val ANIMATION_SMOOTH = Animation(Animation.Type.SMOOTH, .4f)
        private const val STEP_ZOOM = 1.0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
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

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }

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
                        detailsDialog(getString(R.string.map_location_not_granted), getString(R.string.map_allow_location))
                        initMap(DEFAULT_LOCATION)
                    }
                } else {
                    detailsDialog(getString(R.string.map_location_not_granted), getString(R.string.map_allow_location))
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
                    detailsDialog(getString(R.string.map_location_is_off), getString(R.string.map_turn_location_on))
                    initMap(DEFAULT_LOCATION)
                } else {
                    initMap(Point(location.latitude, location.longitude))
                    detailsDialog(getString(R.string.map_location_is_off), getString(R.string.map_turn_location_on))
                }
            }
        } else {
            explanationDialog()
        }
    }

    private fun explanationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.map_geolocation_access))
            .setMessage(getString(R.string.map_need_access))
            .setPositiveButton(getString(R.string.map_allow_access)) { _, _ -> requestPermission() }
            .setNegativeButton(getString(R.string.map_deny_access)) { dialog, _ ->
                dialog.cancel()
                initMap(DEFAULT_LOCATION)
            }
            .create()
            .show()
    }

    private fun initMap(location: Point) {
        map.move(CameraPosition(location, DEFAULT_ZOOM, AZIMUTH, TILT))
        map.addCameraListener(cameraListener)
        viewModel.setVisibleRegion(map.visibleRegion)
        binding.apply {
            mapButtonSearch.setOnClickListener { viewModel.startSearch() }
            mapSearchBox.setEndIconOnClickListener { viewModel.reset() }
            mapZoomInButton.setOnClickListener { changeZoom(STEP_ZOOM) }
            mapZoomOutButton.setOnClickListener { changeZoom(-STEP_ZOOM) }

            editQueryTextWatcher = mapEditQuery.doAfterTextChanged { text ->
                if (text.toString() == viewModel.uiState.value.query) return@doAfterTextChanged
                viewModel.setQueryText(text.toString())
            }

            mapEditQuery.setOnEditorActionListener { _, _, _ ->
                viewModel.startSearch()
                true
            }
        }

        viewModel.apply {
            setQueryText(getString(R.string.map_default_search))
            startSearch()
        }

        viewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach {
                val successMapSearchState = it.mapSearchState as? MapSearchState.Success
                val searchItems = successMapSearchState?.items ?: emptyList()
                updateSearchResponsePlacemarks(searchItems)

                if (it.mapSearchState is MapSearchState.Error) {
                    view?.snackMessage(getString(R.string.map_search_error))
                }

                binding.apply {
                    mapEditQuery.apply {
                        if (text.toString() != it.query) {
                            removeTextChangedListener(editQueryTextWatcher)
                            setText(it.query)
                            addTextChangedListener(editQueryTextWatcher)
                        }
                    }
                    mapEditQuery.isEnabled = it.mapSearchState is MapSearchState.Off
                }
            }
            .launchIn(lifecycleScope)

        viewModel.subscribeForSearch().flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
    }

    private fun updateSearchResponsePlacemarks(items: List<MapSearchResponseItem>) {
        map.mapObjects.clear()
        val imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.icon_point)
        items.forEach {
            map.mapObjects.addPlacemark(
                it.point,
                imageProvider,
                IconStyle().apply { scale = 0.4f }
            ).apply {
                addTapListener(searchResultPlacemarkTapListener)
                userData = it.geoObject
            }
        }
    }

    private fun changeZoom(value: Float) {
        with (map.cameraPosition) {
            map.move(
                CameraPosition(target, zoom + value, azimuth, tilt),
                ANIMATION_SMOOTH,
                null
            )
            viewModel.startSearch()
        }
    }

    private fun detailsDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton(getString(R.string.map_ok)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapViewMap.onStart()
    }

    override fun onStop() {
        binding.mapViewMap.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
        println("map stop")
    }


    override fun onDestroy() {
        super.onDestroy()
        println("map destroy")
    }
}