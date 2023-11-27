package com.example.mypet.domain.map

import com.yandex.mapkit.geometry.BoundingBox

sealed interface MapSearchState {
    data object Off : MapSearchState
    data object Loading : MapSearchState
    data object Error : MapSearchState
    data class Success(
        val items: List<MapSearchResponseItem>,
        val zoomToItems: Boolean,
        val itemsBoundingBox: BoundingBox,
    ) : MapSearchState
}