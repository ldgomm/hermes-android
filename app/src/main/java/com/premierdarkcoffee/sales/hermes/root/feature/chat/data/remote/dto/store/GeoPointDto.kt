package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store

import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint
import kotlinx.serialization.Serializable

@Serializable
data class GeoPointDto(val type: String, val coordinates: List<Double>) {

    fun toGeoPoint(): GeoPoint {
        return GeoPoint(type, coordinates)
    }
}
