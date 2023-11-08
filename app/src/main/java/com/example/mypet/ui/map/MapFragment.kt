package com.example.mypet.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.mypet.app.R
import com.example.mypet.app.databinding.FragmentMapBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
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

class MapFragment : Fragment() {

    private var _ui: FragmentMapBinding? = null
    private val ui get() = _ui!!
    private val map by lazy { ui.mapView.mapWindow.map }
    private val viewModel: MapViewModel by viewModels()
    private lateinit var editQueryTextWatcher: TextWatcher

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
        map.addCameraListener(cameraListener)
        viewModel.setVisibleRegion(map.visibleRegion)
        ui.apply {
            buttonSearch.setOnClickListener { viewModel.startSearch() }
            searchBox.setEndIconOnClickListener { viewModel.reset() }

            editQueryTextWatcher = editQuery.doAfterTextChanged { text ->
                if (text.toString() == viewModel.uiState.value.query) return@doAfterTextChanged
                viewModel.setQueryText(text.toString())
            }

            editQuery.setOnEditorActionListener { _, _, _ ->
                viewModel.startSearch()
                true
            }
        }

        viewModel.apply {
            setQueryText(getString(R.string.default_search_query))
            startSearch()
        }

        viewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach {
                val successSearchState = it.searchState as? SearchState.Success
                val searchItems = successSearchState?.items ?: emptyList()
                updateSearchResponsePlacemarks(searchItems)

                if (it.searchState is SearchState.Error) {
                    view?.snackMessage(getString(R.string.search_error))
                }

                ui.apply {
                    editQuery.apply {
                        if (text.toString() != it.query) {
                            removeTextChangedListener(editQueryTextWatcher)
                            setText(it.query)
                            addTextChangedListener(editQueryTextWatcher)
                        }
                    }
                    editQuery.isEnabled = it.searchState is SearchState.Off
                }
            }
            .launchIn(lifecycleScope)

        viewModel.subscribeForSearch().flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
    }

    private fun updateSearchResponsePlacemarks(items: List<SearchResponseItem>) {
        map.mapObjects.clear()
        val imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.icon_point)
        items.forEach {
            @Suppress("DEPRECATION")
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