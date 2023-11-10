package com.example.mypet.domain.map

import com.yandex.mapkit.GeoObject
import com.yandex.mapkit.geometry.Point

data class MapSearchResponseItem(
    val point: Point,
    val geoObject: GeoObject?,
)