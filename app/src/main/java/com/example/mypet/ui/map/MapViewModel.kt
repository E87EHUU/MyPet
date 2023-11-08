package com.example.mypet.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val searchState = MutableStateFlow<SearchState>(SearchState.Off)

    val uiState: StateFlow<MapUiState> = combine(query, searchState) { query, searchState ->
        MapUiState(query = query, searchState = searchState)
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
        searchState.value = SearchState.Off
        query.value = EMPTY_VALUE
    }

    fun subscribeForSearch(): Flow<*> =
        throttledRegion.filter { it != null }
            .filter { searchState.value is SearchState.Success }
            .mapNotNull { it }
            .onEach { region ->
                searchSession?.let {
                    it.setSearchArea(VisibleRegionUtils.toPolygon(region))
                    it.resubmit(searchSessionListener)
                    searchState.value = SearchState.Loading
                    zoomToSearchResult = false
                }
            }

    private val searchSessionListener = object : SearchListener {
        override fun onSearchResponse(response: Response) {
            val items = response.collection.children.mapNotNull {
                val point = it.obj?.geometry?.firstOrNull()?.point ?: return@mapNotNull null
                SearchResponseItem(point, it.obj)
            }
            val boundingBox = response.metadata.boundingBox ?: return
            searchState.value = SearchState.Success(
                items,
                zoomToSearchResult,
                boundingBox,
            )
        }

        override fun onSearchError(error: Error) {
            searchState.value = SearchState.Error
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
        searchState.value = SearchState.Loading
        zoomToSearchResult = true
    }

    fun uiState(geoObject: GeoObject): Pair<String, String> {
        val geoObjetTypeUiState = geoObject.metadataContainer.getItem(ToponymObjectMetadata::class.java)?.let {
                TypeSpecificState.Toponym(address = it.address.formattedAddress)
            } ?: geoObject.metadataContainer.getItem(BusinessObjectMetadata::class.java)
                ?.let { businessObjectMetadata ->
                    TypeSpecificState.Business(
                        name = businessObjectMetadata.name,
                        workingHours = businessObjectMetadata.workingHours?.text,
                        categories = businessObjectMetadata.categories.map { it.name }
                            .takeIfNotEmpty()?.toSet()?.joinToString(STRING_SEPARATOR),
                        phones = businessObjectMetadata.phones.map { it.formattedNumber }
                            .takeIfNotEmpty()?.joinToString(STRING_SEPARATOR),
                        link = businessObjectMetadata.links.firstOrNull()?.link?.href,
                    )
                } ?: TypeSpecificState.Undefined

        val title = geoObject.name ?: NO_TITLE
        val detailsList = mutableListOf<String?>()
        detailsList.add(geoObject.descriptionText ?: NO_DESCRIPTION)
        detailsList.add("${geoObject.geometry.firstOrNull()?.point?.latitude}, ${geoObject.geometry.firstOrNull()?.point?.longitude}\n")
        when (geoObjetTypeUiState) {
            is TypeSpecificState.Business -> {
                detailsList.add(geoObjetTypeUiState.categories)
                detailsList.add(geoObjetTypeUiState.workingHours)
                detailsList.add(geoObjetTypeUiState.phones)
                detailsList.add(geoObjetTypeUiState.link)
            }
            is TypeSpecificState.Toponym -> {
                detailsList.add(geoObjetTypeUiState.address)
            }
            is TypeSpecificState.Undefined -> {}
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
