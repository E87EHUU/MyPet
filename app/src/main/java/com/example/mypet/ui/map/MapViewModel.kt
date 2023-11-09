package com.example.mypet.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypet.domain.map.MapSearchResponseItem
import com.example.mypet.domain.map.MapSearchState
import com.example.mypet.domain.map.MapTypeSpecificState
import com.example.mypet.domain.map.MapUiState
import com.yandex.mapkit.GeoObject
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.map.VisibleRegion
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.search.BusinessObjectMetadata
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.Session.SearchListener
import com.yandex.mapkit.search.ToponymObjectMetadata
import com.yandex.runtime.Error
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.seconds

class MapViewModel : ViewModel() {

    companion object {
        private const val NO_TITLE = "No title"
        private const val NO_DESCRIPTION = "No description"
        private const val STRING_SEPARATOR = ", "
        private const val EMPTY_VALUE = ""
    }

    private val searchManager =
        SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
    private var searchSession: Session? = null
    private var zoomToSearchResult = false
    private val region = MutableStateFlow<VisibleRegion?>(null)

    @OptIn(FlowPreview::class)
    private val throttledRegion = region.debounce(1.seconds)
    private val query = MutableStateFlow(EMPTY_VALUE)
    private val mapSearchState = MutableStateFlow<MapSearchState>(MapSearchState.Off)

    val uiState: StateFlow<MapUiState> = combine(query, mapSearchState) { query, searchState ->
        MapUiState(query = query, mapSearchState = searchState)
    }.stateIn(viewModelScope, SharingStarted.Lazily, MapUiState())

    fun setQueryText(value: String) {
        query.value = value
    }

    fun setVisibleRegion(region: VisibleRegion) {
        this.region.value = region
    }

    fun startSearch(searchText: String? = null) {
        val text = searchText ?: query.value
        if (query.value.isEmpty()) return
        val region = region.value?.let {
            VisibleRegionUtils.toPolygon(it)
        } ?: return
        submitSearch(text, region)
    }

    fun reset() {
        searchSession?.cancel()
        searchSession = null
        mapSearchState.value = MapSearchState.Off
        query.value = EMPTY_VALUE
    }

    fun subscribeForSearch(): Flow<*> =
        throttledRegion.filter { it != null }
            .filter { mapSearchState.value is MapSearchState.Success }
            .mapNotNull { it }
            .onEach { region ->
                searchSession?.let {
                    it.setSearchArea(VisibleRegionUtils.toPolygon(region))
                    it.resubmit(searchSessionListener)
                    mapSearchState.value = MapSearchState.Loading
                    zoomToSearchResult = false
                }
            }

    private val searchSessionListener = object : SearchListener {
        override fun onSearchResponse(response: Response) {
            val items = response.collection.children.mapNotNull {
                val point = it.obj?.geometry?.firstOrNull()?.point ?: return@mapNotNull null
                MapSearchResponseItem(point, it.obj)
            }
            val boundingBox = response.metadata.boundingBox ?: return
            mapSearchState.value = MapSearchState.Success(
                items,
                zoomToSearchResult,
                boundingBox,
            )
        }

        override fun onSearchError(error: Error) {
            mapSearchState.value = MapSearchState.Error
        }
    }

    private fun submitSearch(query: String, geometry: Geometry) {
        searchSession?.cancel()
        searchSession = searchManager.submit(
            query,
            geometry,
            SearchOptions().apply {
                resultPageSize = 32
            },
            searchSessionListener
        )
        mapSearchState.value = MapSearchState.Loading
        zoomToSearchResult = true
    }

    fun uiState(geoObject: GeoObject): Pair<String, String> {
        val geoObjetTypeUiState = geoObject.metadataContainer.getItem(ToponymObjectMetadata::class.java)?.let {
                MapTypeSpecificState.Toponym(address = it.address.formattedAddress)
            } ?: geoObject.metadataContainer.getItem(BusinessObjectMetadata::class.java)
                ?.let { businessObjectMetadata ->
                    MapTypeSpecificState.Business(
                        name = businessObjectMetadata.name,
                        workingHours = businessObjectMetadata.workingHours?.text,
                        categories = businessObjectMetadata.categories.map { it.name }
                            .takeIfNotEmpty()?.toSet()?.joinToString(STRING_SEPARATOR),
                        phones = businessObjectMetadata.phones.map { it.formattedNumber }
                            .takeIfNotEmpty()?.joinToString(STRING_SEPARATOR),
                        link = businessObjectMetadata.links.firstOrNull()?.link?.href,
                    )
                } ?: MapTypeSpecificState.Undefined

        val title = geoObject.name ?: NO_TITLE
        val detailsList = mutableListOf<String?>()
        detailsList.add(geoObject.descriptionText ?: NO_DESCRIPTION)
        detailsList.add("${geoObject.geometry.firstOrNull()?.point?.latitude}, ${geoObject.geometry.firstOrNull()?.point?.longitude}\n")
        when (geoObjetTypeUiState) {
            is MapTypeSpecificState.Business -> {
                detailsList.add(geoObjetTypeUiState.categories)
                detailsList.add(geoObjetTypeUiState.workingHours)
                detailsList.add(geoObjetTypeUiState.phones)
                detailsList.add(geoObjetTypeUiState.link)
            }
            is MapTypeSpecificState.Toponym -> {
                detailsList.add(geoObjetTypeUiState.address)
            }
            is MapTypeSpecificState.Undefined -> {}
        }

        return Pair(title, detailsList.formatDetails())
    }

    private fun List<String?>.formatDetails(): String {
        val detailsString = StringBuilder()
        this.forEach {
            if (!it.isNullOrBlank()) detailsString.append("$it\n")
        }
        return detailsString.toString()
    }

    private fun <T> List<T>.takeIfNotEmpty(): List<T>? = takeIf { it.isNotEmpty() }
}
