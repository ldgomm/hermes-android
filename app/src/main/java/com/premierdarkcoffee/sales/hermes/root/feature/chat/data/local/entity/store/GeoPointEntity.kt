package com.premierdarkcoffee.sales.hermes.root.feature.chat.data.local.entity.store

import com.premierdarkcoffee.sales.hermes.root.feature.chat.data.remote.dto.store.GeoPointDto
import com.premierdarkcoffee.sales.hermes.root.feature.chat.domain.model.store.GeoPoint

data class GeoPointEntity(
    val type: String,
    val coordinates: List<Double>
) {

    fun toGeoPoint(): GeoPoint {
        return GeoPoint(type, coordinates)
    }


    fun toGeoPointDto(): GeoPointDto {
        return GeoPointDto(type, coordinates)
    }
}
