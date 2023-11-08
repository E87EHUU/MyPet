package com.example.mypet.ui.map

import com.yandex.mapkit.GeoObject
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Point

data class MapUiState(
    val query: String = "",
    val searchState: SearchState = SearchState.Off,
)

sealed interface SearchState {
    data object Off : SearchState
    data object Loading : SearchState
    data object Error : SearchState
    data class Success(
        val items: List<SearchResponseItem>,
        val zoomToItems: Boolean,
        val itemsBoundingBox: BoundingBox,
    ) : SearchState
}

sealed interface TypeSpecificState {
    data class Toponym(val address: String) : TypeSpecificState

    data class Business(
        val name: String,
        val workingHours: String?,
        val categories: String?,
        val phones: String?,
        val link: String?,
    ) : TypeSpecificState

    data object Undefined : TypeSpecificState
}

data class SearchResponseItem(
    val point: Point,
    val geoObject: GeoObject?,
)