package com.example.mypet.domain.map

sealed interface MapTypeSpecificState {
    data class Toponym(val address: String) : MapTypeSpecificState

    data class Business(
        val name: String,
        val workingHours: String?,
        val categories: String?,
        val phones: String?,
        val link: String?,
    ) : MapTypeSpecificState

    data object Undefined : MapTypeSpecificState
}